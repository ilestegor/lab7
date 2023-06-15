package common.command;

import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;


/**
 * Class contains implementation of clear stuff.command
 * Clears collection
 *
 * @author ilestegor
 */
public class ClearCommand extends Command {
    private final RegistrationCode registrationCode;

    public ClearCommand(ServerCollectionManager serverCollectionManager, RegistrationCode registrationCode) {
        super("clear", "Команда очищает коллекцию", serverCollectionManager);
        this.registrationCode = registrationCode;

    }

    public ClearCommand(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs()))
            return new RequestFactory().createRequest(getName(), getArgs());
        throw new WrongArgumentException("У команды " + getName() + " не должно быть аргументов!");
    }

    @Override
    public Response execute(Request request) {
        if (!getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
            return getMusicBandCollectionManager().clearCollection(request);
        }
        return new ResponseFactory().createResponse("Коллекция пустая");
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
