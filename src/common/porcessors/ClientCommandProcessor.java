package common.porcessors;

import common.exception.CommandIsNotExecutedException;
import common.exception.RecursionException;
import common.exception.WrongArgumentException;
import common.manager.CommandManager;
import common.network.Request;
import common.utility.Printer;

import java.util.Arrays;

public class ClientCommandProcessor {
    private final Printer printer = new Printer();
    private final int COMMAND_NAME_POSITION = 0;
    private final int ARGUMENT_START_POSITION = 1;


    public Request commandRequest(String[] inputData) {
        String command = inputData[COMMAND_NAME_POSITION];
        String[] args = Arrays.copyOfRange(inputData, ARGUMENT_START_POSITION, inputData.length);
        CommandManager.getClientCommandMap().get(command).setName(command);
        CommandManager.getClientCommandMap().get(command).setArgs(args);
        try {
            return CommandManager.getClientCommandMap().get(command).execute(new Printer());
        } catch (WrongArgumentException ex) {
            printer.printError(ex.getMessage());
        } catch (RecursionException ex) {
            printer.printError(ex.getMessage());
        }
        throw new CommandIsNotExecutedException();
    }

    public boolean commandChecker(String[] inputData) {
        if (CommandManager.executeClient(inputData)) {
            return true;
        } else {
            printer.printNextLine("Такой команды нет! Введите команду help для просмотра списка всех доступных команд");
            return false;
        }
    }
}
