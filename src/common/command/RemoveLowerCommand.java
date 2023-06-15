package common.command;

import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;

/**
 * Class contains implementation of remove_lower stuff.command
 * Removes all elements whoes numberOfParticipants lower than given one
 *
 * @author ilestegor
 */
public class RemoveLowerCommand extends Command {
    private final RegistrationCode registrationCode;

    public RemoveLowerCommand(ServerCollectionManager serverCollectionManager, RegistrationCode registrationCode) {
        super("remove_lower", "Команда удаляет элементы меньше чем заданный (по количеству участников). IMPORTANT: аргумент должен быть id элемента", serverCollectionManager);
        this.registrationCode = registrationCode;
    }

    public RemoveLowerCommand(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs())) {
            return new RequestFactory().createRequest(getName(), getArgs());
        }
        throw new WrongArgumentException("У команды remove_lower должен быть аргумент (id элемента) типа (int)");

    }

    @Override
    public Response execute(Request request) {
        int receivedArg = Integer.parseInt(request.getRequestBody().getArgs()[0]);
        if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
            return new ResponseFactory().createResponse("Коллекция пуста!");
        } else {
            return getMusicBandCollectionManager().removeLower(request, receivedArg);
        }
    }

    @Override
    public boolean checkArgument(String[] inputArgs) {
        if (inputArgs.length == 0) {
            return false;
        } else {
            try {
                Integer.parseInt(inputArgs[0]);
                return true;
            } catch (NumberFormatException ex) {
                return false;
            }
        }
    }

    @Override
    public RegistrationCode getRegistrationCode() {
        return registrationCode;
    }
}
