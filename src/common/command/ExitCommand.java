package common.command;

import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.manager.UserManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import common.utility.Printer;

/**
 * Class contains implementation of exit stuff.command
 * Terminates the application
 *
 * @author ilestegor
 */
public class ExitCommand extends Command {


    public ExitCommand(ServerCollectionManager serverCollectionManager) {
        super("exit", "Команда завершает программу без сохранения результата в файл", serverCollectionManager);
    }

    public ExitCommand() {
    }

    @Override
    public Request execute(Printer printer) {
        if (checkArgument(getArgs())) {
            UserManager.setIsInWork(false);
            return new RequestFactory().createRequest(getName(), getArgs());
        }
        throw new WrongArgumentException();
    }

    @Override
    public Response execute(Request request) {
//            команда сохранения коллекции

        return new ResponseFactory().createResponse("Завершение работы!");
    }

    @Override
    public boolean checkArgument(String[] inputArgs) {
        return inputArgs.length == 0;
    }

}
