package common.porcessors;

import common.command.Command;
import common.exception.CommandIsNotExecutedException;
import common.exception.RecursionException;
import common.exception.WrongArgumentException;
import common.manager.CommandManager;
import common.network.Request;
import common.utility.Printer;

import java.util.Arrays;
import java.util.HashMap;

public class ClientCommandProcessor {
    private final Printer printer = new Printer();
    private final int COMMAND_NAME_POSITION = 0;
    private final int ARGUMENT_START_POSITION = 1;


    public Request commandRequest(String[] inputData, Request request) {
        String command = inputData[COMMAND_NAME_POSITION];
        String[] args = Arrays.copyOfRange(inputData, ARGUMENT_START_POSITION, inputData.length);
        HashMap<String, Command> commandHashMap = CommandManager.getCommandsByRegistration(CommandManager.getClientCommandMap(), request);
        commandHashMap.get(command).setName(command);
        commandHashMap.get(command).setArgs(args);
        try {
            return commandHashMap.get(command).execute();
        } catch (WrongArgumentException ex) {
            printer.printError(ex.getMessage());
        } catch (RecursionException ex) {
            printer.printError(ex.getMessage());
        }
        throw new CommandIsNotExecutedException();
    }

    public boolean commandChecker(String[] inputData, Request request) {
        if (CommandManager.executeClient(inputData, request)) {
            return true;
        } else {
            printer.printNextLine("Такой команды нет! Введите команду help для просмотра списка всех доступных команд");
            return false;
        }
    }
}
