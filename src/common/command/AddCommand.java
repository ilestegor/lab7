package common.command;

import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import server.manager.CreatorManager;
import server.model.MusicBand;

import java.time.LocalDate;

/**
 * Class contains implementation of add stuff.command
 * Adds new element to collection
 *
 * @author ilestegor
 */
public class AddCommand extends Command {

    private final RegistrationCode registrationCode;
    private CreatorManager creatorManager;

    public AddCommand(ServerCollectionManager serverCollectionManager, RegistrationCode registrationCode, CreatorManager creatorManager) {
        super("add", "Команда добавляет новый пользоватлеьский элемент в коллекцию", serverCollectionManager);
        this.registrationCode = registrationCode;
        this.creatorManager = creatorManager;

    }

    public AddCommand(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs()))
            return new RequestFactory().createRequestMusicBand(getName(), getUserManager().requestDataForUserMusicBand());
        throw new WrongArgumentException();
    }

    @Override
    public Response execute(Request request) {
        if (checkMusicBandArgument(request.getRequestBodyMusicBand().getMusicBand())) {
            request.getRequestBodyMusicBand().getMusicBand().setCreationDate(LocalDate.now().atStartOfDay());
            request.getRequestBodyMusicBand().getMusicBand().setCreatorId(creatorManager.findUserByCredentials(request.getCredential()).getId());
            if (getMusicBandCollectionManager().addMusicBandToDB(request.getRequestBodyMusicBand().getMusicBand())) {
                return new ResponseFactory().createResponse("Объект успешео добавлен в коллекцию!");
            } else return new ResponseFactory().createResponse("Объект не добавлен в коллекцию! Попробуйте еще раз");
        }
        return new ResponseFactory().createResponse("У команды add нет аргументов!");
    }

    @Override
    public boolean checkArgument(String[] inputArgs) {
        return inputArgs.length == 0;
    }

    @Override
    public boolean checkMusicBandArgument(MusicBand musicBand) {
        return super.checkMusicBandArgument(musicBand);
    }

    @Override
    public RegistrationCode getRegistrationCode() {
        return registrationCode;
    }
}