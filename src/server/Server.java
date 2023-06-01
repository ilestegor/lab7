package server;

import common.interfaces.BaseWriter;
import common.manager.CommandManager;
import common.manager.ServerCollectionManager;
import common.manager.UserManager;
import common.network.Request;
import common.network.Response;
import common.porcessors.ServerCommandProcessor;
import common.utility.Printer;
import server.network.ServerConnection;
import server.parse.YamlReader;
import server.parse.YamlWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.Scanner;

public class Server {
    Response response;
    ServerConnection serverConnection;
    ServerCollectionManager sc;
    CommandManager cm;
    YamlReader yamlReader;
    BaseWriter yamlWriter;
    Scanner scanner;
    Request request;

    public void run() {
        try {
            serverConnection = new ServerConnection(Integer.parseInt(System.getenv("PORT")));
            sc = new ServerCollectionManager();
            boolean isRunning = true;
            MainServerApp.LOGGER.info("Сервер начал работу");
            cm = new CommandManager(sc, new UserManager());
            yamlReader = new YamlReader(new Printer());
            sc.readToCollection(yamlReader);
            yamlWriter = new YamlWriter(sc);
            scanner = new Scanner(System.in);



            while (isRunning) {
                try {
                    while (request == null){
                        if (System.in.available() > 0) {
                            String line = new Scanner(System.in).nextLine();
                            if ("save".equals(line)) {
                                yamlWriter.write(System.getenv("YamlFile"));
                            }
                            if ("exit".equals(line)) {
                                MainServerApp.LOGGER.info("Завершение работы сервера");
                                System.exit(0);
                            }
                        }
                        request = serverConnection.receiveDataFromClient();
                    }
                    MainServerApp.LOGGER.info("Пакет получен от клиента: "
                            + serverConnection.getDpack().getAddress().getHostName() + " " +
                            serverConnection.getDpack().getPort() + " c командой " + request.getCommandDTO().getCommandName());
                    ServerCommandProcessor scm = new ServerCommandProcessor();
                    response = scm.processCommand(request);
                    serverConnection.sendDataToClient(response);
                    MainServerApp.LOGGER.info("Отправлен ответ на команду "
                            + request.getCommandDTO().getCommandName() + " клиенту " + serverConnection.getDpack().getAddress().getHostName()
                            + " " + serverConnection.getDpack().getPort());
                    request = null;
                    serverConnection.disconnect();
                } catch (IOException ex) {
                    MainServerApp.LOGGER.warning("ТАЙМАУТ");
                } catch (ClassNotFoundException ex) {
                    MainServerApp.LOGGER.warning("Возникла проблема сериализации данных");
                }
            }
        } catch (SocketException ex) {
            MainServerApp.LOGGER.warning("Проблема соединения! Порт недоступен, поменяйте порт в переменной окружения");
        }
    }
}
