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

import java.io.IOException;
import java.net.SocketException;

public class Server {
    Response response;
    ServerConnection serverConnection;
    ServerCollectionManager sc;
    CommandManager cm;
    YamlReader yamlReader;
    YamlWriter yamlWriter;

    public void run() {
        try {
            serverConnection = new ServerConnection(32458);
            sc = new ServerCollectionManager();
            boolean isRunning = true;
            MainServerApp.LOGGER.info("Сервер начал работу");
            cm = new CommandManager(sc, new UserManager());
            yamlReader = new YamlReader(new Printer());
            sc.readToCollection(yamlReader);
            yamlWriter = new YamlWriter(sc);

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
                    yamlWriter.write(System.getenv("YamlFile"));
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
