package common.command;

import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;

/**
 * Class contains implementation of remove_by_id stuff.command
 * Removes element according given id
 *
 * @author ilestegor
 */
public class RemoveByIdCommand extends Command {
    private final RegistrationCode registrationCode;

    public RemoveByIdCommand(ServerCollectionManager serverCollectionManager, RegistrationCode registrationCode) {
        super("remove_by_id", "Команда удаляет элемент коллекции в соответсвии с его id", serverCollectionManager);
        this.registrationCode = registrationCode;
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs())) {
            return new RequestFactory().createRequest(getName(), getArgs());
        }
        throw new WrongArgumentException("У команды remove_by_id должен быть аргумент (id элемента коллекции) типа (int)!");

    }

    public RemoveByIdCommand(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public Response execute(Request request) {
        if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
            return new ResponseFactory().createResponse("Коллекция пуста!");
        } else {
            int id = Integer.parseInt(getArgs()[0]);
            return getMusicBandCollectionManager().removeFromCollection(request, id);

        }
    }

    @Override
    public boolean checkArgument(String[] inputArgs) {
        if (inputArgs.length == 0) {
            return false;
        } else {
            try {
                Integer.parseInt(getArgs()[0]);
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
