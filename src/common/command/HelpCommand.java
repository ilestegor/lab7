package common.command;

import common.exception.WrongArgumentException;
import common.manager.CommandManager;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import common.utility.Printer;

import java.util.ArrayList;

/**
 * Class contains implementation of help stuff.command\
 * Outputs list of available commands
 *
 * @author ilestegor
 */
public class HelpCommand extends Command {
    public HelpCommand(ServerCollectionManager serverCollectionManager) {
        super("help", "Команда выводит список достпуных комманд и их описание", serverCollectionManager);
    }

    public HelpCommand() {
    }

    @Override
    public Request execute(Printer printer) {
        if (checkArgument(getArgs()))
            return new RequestFactory().createRequest(getName(), getArgs());
        throw new WrongArgumentException("У команды help нет аргументов, попробуйте еще раз!");
    }

    @Override
    public Response execute(Request request) {
        int[] counter = new int[1];
        counter[0]++;
        ArrayList<String> helpCommandList = new ArrayList<>();
        CommandManager.getServerCommandMap().forEach((key, value) -> helpCommandList.add(counter[0]++ + ". " + key + " - " + value.getDescription()));
        return new ResponseFactory().createResponse(String.join("\n", helpCommandList));
    }

    @Override
    public boolean checkArgument(String[] inputArgs) {
        return inputArgs.length == 0;
    }
}
