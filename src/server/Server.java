package server;

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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.Scanner;

public class Server {
    Response response;
    ServerConnection serverConnection;
    ServerCollectionManager sc;
    CommandManager cm;
    YamlReader yamlReader;
    YamlWriter yamlWriter;
    Scanner scanner;
    InputStream inputStream;

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
            inputStream = new BufferedInputStream(System.in);

            while (isRunning) {
                try {
                    Request request = serverConnection.receiveDataFromClient();
                    MainServerApp.LOGGER.info("Пакет получен от клиента: "
                            + serverConnection.getDpack().getAddress().getHostName() + " " +
                            serverConnection.getDpack().getPort() + " c командой " + request.getCommandDTO().getCommandName());
                    System.out.println();
                    ServerCommandProcessor scm = new ServerCommandProcessor();
                    response = scm.processCommand(request);
                    serverConnection.sendDataToClient(response);
                    MainServerApp.LOGGER.info("Отправлен ответ на команду "
                            + request.getCommandDTO().getCommandName() + " клиенту " + serverConnection.getDpack().getAddress().getHostName()
                            + " " + serverConnection.getDpack().getPort());

                    if (inputStream.available() > 0) {
                        if (scanner.nextLine().equals("save")){
                            yamlWriter.write(System.getenv("YamlFile"));
                        } continue;
                    }
                    serverConnection.disconnect();
                } catch (IOException ex) {
                    MainServerApp.LOGGER.warning("Ошибка IO");
                } catch (ClassNotFoundException ex) {
                    MainServerApp.LOGGER.warning("Возникла проблема сериализации данных");
                }
            }
        } catch (SocketException ex) {
            MainServerApp.LOGGER.warning("Проблема соединения!");
        }
    }
}
