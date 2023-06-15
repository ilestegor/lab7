package server;

import common.auth.RegistrationCode;
import common.manager.CommandManager;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.Response;
import common.porcessors.ServerCommandProcessor;
import common.utility.Serializer;
import org.apache.commons.lang3.tuple.Pair;
import server.dao.MusicBandDaoImpl;
import server.dao.UserDAOImpl;
import server.manager.CreatorManager;
import server.network.DBConnection;
import server.network.ServerConnection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
    ServerConnection serverConnection;
    ServerCollectionManager sc;
    CommandManager cm;
    CreatorManager creatorManager;
    UserDAOImpl userDAO;
    MusicBandDaoImpl musicBandDao;
    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    LinkedList<Pair<DatagramPacket, byte[]>> listOfRequests = new LinkedList<>();

    public void run() {
        try {
            serverConnection = new ServerConnection(Integer.parseInt(System.getenv("PORT")));
            boolean isRunning = true;
            MainServerApp.LOGGER.info("Сервер начал работу");
            DBConnection dbConnection = new DBConnection();
            try {
                java.sql.Connection connection = dbConnection.connect();
                userDAO = new UserDAOImpl(connection);
                musicBandDao = new MusicBandDaoImpl(connection);
                creatorManager = new CreatorManager(userDAO);
            } catch (FileNotFoundException e) {
                MainServerApp.LOGGER.info("Файл для подключения к базе данных отсутствует. База данных не подключена");
                isRunning = false;
            } catch (ClassNotFoundException e) {
                MainServerApp.LOGGER.info("Драйвер для базы данных не найден! База данных не подключена");
                isRunning = false;
            } catch (SQLException e) {
                MainServerApp.LOGGER.info("Проблема с подключением к БД");
                isRunning = false;
            } catch (IOException ex) {
                MainServerApp.LOGGER.info("Файл для подключения к базе данных недоступен! База данных не подключена");
                isRunning = false;
            }
            sc = new ServerCollectionManager(musicBandDao, creatorManager);
            sc.readToCollection();
            cm = new CommandManager(sc, creatorManager, creatorManager);
            ServerCommandProcessor scm = new ServerCommandProcessor(cm);
            while (isRunning) {
                Pair<DatagramPacket, byte[]> requestPair = serverConnection.receiveDataFromClient();
                if (requestPair.getRight().length != 0) {
                    cachedThreadPool.submit(() -> {
                        listOfRequests.add(requestPair);
                        System.out.println(Thread.currentThread());
                        forkJoinPool.invoke(new RecursiveAction() {
                            @Override
                            protected void compute() {
                                System.out.println(Thread.currentThread());
                                Response response = new Response();
                                Pair<DatagramPacket, byte[]> requestFormList = listOfRequests.getLast();
                                SocketAddress addr = requestFormList.getLeft().getSocketAddress();
                                Request request = null;
                                try {
                                    request = (Request) Serializer.deserialize(requestFormList.getRight());
                                } catch (IOException | ClassNotFoundException e) {
                                    MainServerApp.LOGGER.info("Проблема сериализации запроса");
                                }
                                request.setRegistrationCode(request.getRegistrationCode());
                                response.setRegistrationCode(RegistrationCode.NOT_REGISTERED);
                                if (creatorManager.checkIfRegistered(request) || creatorManager.checkIfLogged(request)) {
                                    response.setRegistrationCode(RegistrationCode.REGISTERED);
                                } else {
                                    response.setRegistrationCode(RegistrationCode.NOT_REGISTERED);
                                }
                                MainServerApp.LOGGER.info("Пакет получен от клиента: "
                                        + addr + " " +
                                        addr + " c командой " + request.getCommandDTO().getCommandName());
                                Response response1 = scm.processCommand(request);
                                response.setMessage(response1.getMessage());
                                if (response1.getRegistrationCode() != null) {
                                    response.setRegistrationCode(response1.getRegistrationCode());
                                }
                                new Thread(() -> {
                                    try {
                                        serverConnection.sendDataToClient(response, listOfRequests.getLast().getLeft().getSocketAddress());
                                        listOfRequests.poll();
                                    } catch (IOException e) {
                                        MainServerApp.LOGGER.info("Не удалось отправить ответ клиенту");
                                    }
                                }).start();
                                MainServerApp.LOGGER.info("Отправлен ответ на команду "
                                        + request.getCommandDTO().getCommandName() + " клиенту "
                                        + addr
                                        + " "
                                        + addr);
                                try {
                                    serverConnection.disconnect();
                                } catch (IOException e) {
                                    MainServerApp.LOGGER.info("Проблема сервера");
                                }
                            }
                        });
                    });
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            MainServerApp.LOGGER.warning("Проблема соединения! Порт недоступен, поменяйте порт в переменной окружения");
        }
    }
}
