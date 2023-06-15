package common.command;

import common.auth.Credential;
import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.CommandDTO;
import common.network.Request;
import common.network.RequestBody;
import common.network.Response;
import common.utility.passwordHashStrategy.SHA1Login;
import server.manager.DBUserManager;

public class LoginCommand extends Command {
    private DBUserManager dbUserManager;
    private final RegistrationCode registrationCode;

    public LoginCommand(ServerCollectionManager serverCollectionManager, DBUserManager dbUserManager, RegistrationCode registrationCode) {
        super("login", "Команда для входа в аккаунт", serverCollectionManager);
        this.dbUserManager = dbUserManager;
        this.registrationCode = registrationCode;
    }

    public LoginCommand(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public Response execute(Request request) {
        return dbUserManager.login(request);
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs())) {
            Request request = new Request(new CommandDTO("login"), new RequestBody(getArgs()));
            request.setCredential(new Credential(getUserManager().requestLoginFromUser(), SHA1Login.hashPassword(getUserManager().requestPasswordFromUser())));
            return request;
        }
        throw new WrongArgumentException("У команды login нет аргументов");
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
