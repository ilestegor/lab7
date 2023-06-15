package common.command;

import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.manager.UserManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;

/**
 * Class contains implementation of exit stuff.command
 * Terminates the application
 *
 * @author ilestegor
 */
public class ExitCommand extends Command {

    private final RegistrationCode registrationCode;

    public ExitCommand(ServerCollectionManager serverCollectionManager, RegistrationCode registrationCode) {
        super("exit", "Команда завершает программу без сохранения результата в файл", serverCollectionManager);
        this.registrationCode = registrationCode;
    }

    public ExitCommand(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs())) {
            UserManager.setIsInWork(false);
            return new RequestFactory().createRequest(getName(), getArgs());
        }
        throw new WrongArgumentException();
    }

    @Override
    public Response execute(Request request) {
        return new ResponseFactory().createResponse("Завершение работы!");
    }

    @Override
    public boolean checkArgument(String[] inputArgs) {
        return inputArgs.length == 0;
    }

    @Override
    public RegistrationCode getRegistrationCode() {
        return registrationCode;
    }
}
