package client;

import client.network.ClientConnection;
import common.auth.Credential;
import common.auth.RegistrationCode;
import common.exception.CommandIsNotExecutedException;
import common.manager.CommandManager;
import common.manager.ServerCollectionManager;
import common.manager.UserManager;
import common.network.Request;
import common.network.Response;
import common.network.ResponseFactory;
import common.porcessors.ClientCommandProcessor;
import common.utility.Printer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.time.LocalDateTime;
import java.util.Objects;

public class Client {
    ClientConnection clientConnection;
    UserManager userManager;
    CommandManager manager;
    ClientCommandProcessor cmp;
    Credential credentials;
    Request request;

    public void run() {
        Printer printer = new Printer();
        try {
            clientConnection = new ClientConnection(InetAddress.getLocalHost(), Integer.parseInt(System.getenv("PORT")));
            clientConnection.connect(clientConnection.getAddress().getAddress(), clientConnection.getPort());
            userManager = new UserManager();
            printer.printNextLine("---------- " + LocalDateTime.now().toString().substring(0, 10) + " ---------");
            printer.printNextLine("Приложение запущено!");
            printer.printNextLine("Введите команду register для регистрации или команду login для входа в аккаунт");


            manager = new CommandManager(new ServerCollectionManager(), userManager);
            cmp = new ClientCommandProcessor();
            request = new Request();
            request.setRegistrationCode(RegistrationCode.NOT_REGISTERED);
            while (userManager.isIsInWork()) {
                userManager.requestCommand();
                while (userManager.getInputCommand().size() != 0) {
                    String[] str = userManager.requestPlainCommand(Objects.requireNonNull(userManager.getInputCommand().poll()));
                    if (cmp.commandChecker(str, request)) {
                        try {
                            Request tempRequest = cmp.commandRequest(str, request);
                            request.setCommandDTO(tempRequest.getCommandDTO());
                            request.setRequestBody(tempRequest.getRequestBody());
                            request.setRequestBodyMusicBand(tempRequest.getRequestBodyMusicBand());
                            request.setCredential(tempRequest.getCredential());
                            if (request == null) {
                                continue;
                            }
                            if (request.getCredential() == null) {
                                request.setCredential(this.credentials);
                            } else {
                                this.credentials = request.getCredential();
                            }
                            clientConnection.sendCommand(request);
                            ResponseFactory responseFactory = new ResponseFactory();
                            Response response = clientConnection.receiveResult();
                            responseFactory.createResponse(response.getMessage());
                            System.out.println("\n" + response.getMessage() + "\n");
                            if (response.getRegistrationCode().equals(RegistrationCode.REGISTERED)) {
                                request.setRegistrationCode(RegistrationCode.REGISTERED);
                            } else {
                                credentials = null;
                                request.setRegistrationCode(RegistrationCode.NOT_REGISTERED);
                            }
                        } catch (CommandIsNotExecutedException ex) {
                            printer.printError(ex.getMessage());
                        } catch (PortUnreachableException ex) {
                            printer.printError("Сервер недоступен!");
                        }
                    }
                }

            }
            if (clientConnection.getClient().isConnected()) {
                clientConnection.getClient().disconnect();
                clientConnection.getClient().close();
            }
        } catch (IOException ex) {
            printer.printError("Ошибка IO");
        } catch (ClassNotFoundException ex) {
            printer.printError("Ошибка сериализация данных");
        }
    }
}
