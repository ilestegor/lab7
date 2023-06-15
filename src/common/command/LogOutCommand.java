package common.command;

import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.CommandDTO;
import common.network.Request;
import common.network.RequestBody;
import common.network.Response;
import server.manager.DBUserManager;

public class LogOutCommand extends Command {
    private final DBUserManager dbUserManager;
    private final RegistrationCode registrationCode;

    public LogOutCommand(ServerCollectionManager serverCollectionManager, DBUserManager dbUserManager, RegistrationCode registrationCode) {
        super("logout", "Выход из аккаунта", serverCollectionManager);
        this.registrationCode = registrationCode;
        this.dbUserManager = dbUserManager;
    }

    @Override
    public Response execute(Request request) {
        return dbUserManager.logOut(request);
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs())) {
            Request request = new Request(new CommandDTO(getName()), new RequestBody(getArgs()));
            request.setCredential(null);
            return request;
        }
        throw new WrongArgumentException();
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
