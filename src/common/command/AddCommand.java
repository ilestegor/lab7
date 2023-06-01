package common.command;

import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import common.utility.Printer;
import server.model.MusicBand;

import java.time.LocalDate;
import java.util.Random;

/**
 * Class contains implementation of add stuff.command
 * Adds new element to collection
 *
 * @author ilestegor
 */
public class AddCommand extends Command {


    public AddCommand(ServerCollectionManager serverCollectionManager) {
        super("add", "Команда добавляет новый пользоватлеьский элемент в коллекцию", serverCollectionManager);
    }

    public AddCommand() {
    }

    @Override
    public Request execute(Printer printer) {
        if (checkArgument(getArgs()))
            return new RequestFactory().createRequestMusicBand(getName(), getUserManager().requestDataForUserMusicBand());
        throw new WrongArgumentException();
    }

    @Override
    public Response execute(Request request) {
        if (checkMusicBandArgument(request.getRequestBodyMusicBand().getMusicBand())) {
            Random random = new Random();
            request.getRequestBodyMusicBand().getMusicBand().setId(random.nextLong(2, getMAX_ID()));
            request.getRequestBodyMusicBand().getMusicBand().setCreationDate(LocalDate.now().atStartOfDay());
            if (getMusicBandCollectionManager().getMusicBandLinkedList().add(request.getRequestBodyMusicBand().getMusicBand())) {
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
}
