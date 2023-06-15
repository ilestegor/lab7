package common.command;

import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import common.utility.Printer;
import server.model.MusicBand;

import java.util.Optional;

/**
 * Class contains implementation of update stuff.command
 * Updates element's data according id
 *
 * @author ilestegor
 */
public class UpdateIdCommand extends Command {
    private final RegistrationCode registrationCode;
    Printer printer = new Printer();

    public UpdateIdCommand(ServerCollectionManager serverCollectionManager, RegistrationCode registrationCode) {
        super("update", "Команда принимает в виде аргумента id элеменета, который находится в коллекции и обновляет его данные", serverCollectionManager);
        this.registrationCode = registrationCode;
    }

    public UpdateIdCommand(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    //execute for id -> server gives us response -> by response we need not call another command to collect data from user
    @Override
    public Request execute() {
        if (checkArgument(getArgs())) {
            return new RequestFactory().createRequest(getName(), getArgs());
        }
        throw new WrongArgumentException("У команды update должен быть аргумент id (id элемента, значение которого вы хотите обновить) типа (int). Попробуйте еще раз!");
    }

    @Override
    public Response execute(Request request) {
        if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
            return new ResponseFactory().createResponse("Коллекция пуста!");
        } else {
            long userInputId = Long.parseLong(request.getRequestBody().getArgs()[0]);
            Optional<MusicBand> targetMusicBand = getMusicBandCollectionManager().findModelByIdAndCreatorId(request, Math.toIntExact(userInputId));
            if (getMusicBandCollectionManager().getIdContainer().contains(userInputId) && targetMusicBand.isPresent()) {
                return new ResponseFactory().createResponseWithNoMessage(true, "create");
            }
            return new ResponseFactory().createResponseWithMessage("Такого id " + userInputId + "  нет или вы пытаетесь обновить объект, который вам не принадлежит", false, "create");
        }
    }

    @Override
    public boolean checkArgument(String[] inputArgs) {
        try {
            if (inputArgs.length == 0) {
                return false;
            } else {
                Integer.parseInt(getArgs()[0]);
                return true;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    @Override
    public RegistrationCode getRegistrationCode() {
        return registrationCode;
    }
}
