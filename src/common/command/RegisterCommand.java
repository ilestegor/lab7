package common.command;

import common.auth.Credential;
import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.CommandDTO;
import common.network.Request;
import common.network.RequestBody;
import common.network.Response;
import common.utility.passwordHashStrategy.SHA1Registration;
import server.manager.DBUserManager;

public class RegisterCommand extends Command {
    private DBUserManager dbUserManager;
    private final RegistrationCode registrationCode;

    public RegisterCommand(ServerCollectionManager serverCollectionManager, DBUserManager dbUserManager, RegistrationCode registrationCode) {
        super("register", "Команда для регистрации новых пользователей", serverCollectionManager);
        this.dbUserManager = dbUserManager;
        this.registrationCode = registrationCode;
    }

    public RegisterCommand(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public Response execute(Request request) {
        return dbUserManager.register(request);
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs())) {
            Request request = new Request(new CommandDTO(getName()), new RequestBody(getArgs()));
            request.setCredential(new Credential(getUserManager().requestLoginFromUser(), SHA1Registration.hashPassword(getUserManager().requestPasswordFromUser()), SHA1Registration.getSalt()));
            return request;
        }
        throw new WrongArgumentException("У команды register нет аргументов");
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
