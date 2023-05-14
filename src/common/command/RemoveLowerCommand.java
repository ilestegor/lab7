package common.command;

import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import common.utility.Printer;
import server.model.MusicBand;

import java.util.Iterator;

/**
 * Class contains implementation of remove_lower stuff.command
 * Removes all elements whoes numberOfParticipants lower than given one
 *
 * @author ilestegor
 */
public class RemoveLowerCommand extends Command {
    public RemoveLowerCommand(ServerCollectionManager serverCollectionManager) {
        super("remove_lower", "Команда удаляет элементы меньше чем заданный (по количеству участников). IMPORTANT: аргумент должен быть id элемента", serverCollectionManager);
    }

    public RemoveLowerCommand() {
    }

    @Override
    public Request execute(Printer printer) {
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
            if (getMusicBandCollectionManager().getMusicBandLinkedList().contains(getMusicBandCollectionManager().findModelById(receivedArg))) {
                int targetNumber = 0;
                for (MusicBand m : getMusicBandCollectionManager().getMusicBandLinkedList()) {
                    if (m.getId() == receivedArg) {
                        targetNumber = m.getNumberOfParticipants();
                        break;
                    }
                }
                Iterator<MusicBand> iterator = getMusicBandCollectionManager().getMusicBandLinkedList().iterator();
                while (iterator.hasNext()) {
                    MusicBand ms = iterator.next();
                    if (targetNumber > ms.getNumberOfParticipants()) {
                        iterator.remove();
                    }
                }
                return new ResponseFactory().createResponse("Элементы меньшие чем указнный успешно удалены!");
            } else {
                return new ResponseFactory().createResponse("id c номером " + request.getRequestBody().getArgs()[0] + " нет в коллекции");
            }
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
}
