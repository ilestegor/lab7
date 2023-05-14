package common.command;

import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import common.utility.Printer;

/**
 * Class contains implementation of update stuff.command
 * Updates element's data according id
 *
 * @author ilestegor
 */
public class UpdateIdCommand extends Command {

    public UpdateIdCommand(ServerCollectionManager serverCollectionManager) {
        super("update", "Команда принимает в виде аргумента id элеменета, который находится в коллекции и обновляет его данные", serverCollectionManager);
    }

    public UpdateIdCommand() {
    }

    @Override
    public Request execute(Printer printer) {
        if (checkArgument(getArgs()))
            return new RequestFactory().createRequestMusicBandWithArgs(getName(), getArgs(), getUserManager().requestDataForUserMusicBand());
        throw new WrongArgumentException("У команды update должен быть аргумент id (id элемента, значение которого вы хотите обновить) типа (int). Попробуйте еще раз!");
    }

    @Override
    public Response execute(Request request) {
        if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
            return new ResponseFactory().createResponse("Коллекция пуста!");
        } else {
            int userInputId = Integer.parseInt(request.getRequestBody().getArgs()[0]);
            return getMusicBandCollectionManager().updateElementInCollection(getMusicBandCollectionManager().findModelById(userInputId), userInputId, request);
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
}
