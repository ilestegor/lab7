package common.command;

import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import server.model.MusicBand;

import java.util.ArrayList;

/**
 * Class contains implementation of show stuff.command
 * Outputs all elements in collection
 *
 * @author ilestegor
 */
public class ShowCommand extends Command {
    private final RegistrationCode registrationCode;

    public ShowCommand(ServerCollectionManager serverCollectionManager, RegistrationCode registrationCode) {
        super("show", "Команда выводит все элементы коллекции", serverCollectionManager);
        this.registrationCode = registrationCode;
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs())) {
            return new RequestFactory().createRequest(getName(), getArgs());
        }
        throw new WrongArgumentException("Команда " + getName() + " не имеет аргументов, попробуйте ввести команду без аргументов!");
    }

    public ShowCommand(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public Response execute(Request request) {
        if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
            return new ResponseFactory().createResponse("Коллекция пустая!");
        } else {
            ArrayList<String> musicBandShowCommandList = new ArrayList<>();
            getMusicBandCollectionManager().getMusicBandLinkedList().sort((o1, o2) -> (int) (o1.getCoordinates().getX() - o2.getCoordinates().getX()));
            getMusicBandCollectionManager().getMusicBandLinkedList().stream().map(MusicBand::toString).forEachOrdered(musicBandShowCommandList::add);
            return new ResponseFactory().createResponse(String.join("", musicBandShowCommandList));
        }
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
