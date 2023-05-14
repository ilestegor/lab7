package common.command;

import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import common.utility.Printer;
import server.model.MusicBand;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Class contains implementation of show stuff.command
 * Outputs all elements in collection
 *
 * @author ilestegor
 */
public class ShowCommand extends Command {
    public ShowCommand(ServerCollectionManager serverCollectionManager) {
        super("show", "Команда выводит все элементы коллекции", serverCollectionManager);
    }

    @Override
    public Request execute(Printer printer) {
        if (checkArgument(getArgs())) {
            return new RequestFactory().createRequest(getName(), getArgs());
        }
        throw new WrongArgumentException("Команда " + getName() + " не имеет аргументов, попробуйте ввести команду без аргументов!");
    }

    public ShowCommand() {
    }

    @Override
    public Response execute(Request request) {
        if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
            return new ResponseFactory().createResponse("Коллекция пустая!");
        } else {
            ArrayList<String> musicBandShowCommandList = new ArrayList<>();
            getMusicBandCollectionManager().getMusicBandLinkedList().stream().map(MusicBand::toString).forEachOrdered(musicBandShowCommandList::add);
            return new ResponseFactory().createResponse(String.join("", musicBandShowCommandList));
        }
    }

    @Override
    public boolean checkArgument(String[] inputArgs) {
        return inputArgs.length == 0;
    }
}
