package common.command;

import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import common.utility.Printer;

import java.time.LocalDate;
import java.util.Random;

/**
 * Class contains implementation of insert_at stuff.command
 * Inserts stuff.command at given index
 *
 * @author ilestegor
 */
public class InsertAtIndexCommand extends Command {
    public InsertAtIndexCommand(ServerCollectionManager serverCollectionManager) {
        super("insert_at", "Команда вставляет новый элемент в позицию, равную заданной", serverCollectionManager);
    }

    public InsertAtIndexCommand() {
    }

    @Override
    public Request execute(Printer printer) {
        if (checkArgument(getArgs())) {
            return new RequestFactory().createRequestMusicBandWithArgs(getName(), getArgs(), getUserManager().requestDataForUserMusicBand());
        }
        throw new WrongArgumentException("У команды insert_at должен быть аргумент (позиция типа (int), в которую вы хотите добавить новый элемент)");

    }

    @Override
    public Response execute(Request request) {
        if (Integer.parseInt(request.getRequestBody().getArgs()[0]) > getMusicBandCollectionManager().getMusicBandLinkedList().size() || Integer.parseInt(request.getRequestBody().getArgs()[0]) < 0) {
            return new ResponseFactory().createResponse("Вы не можете добавить элемент в данную позиция, так как эта позиция выходит за пределы массива " +
                    "Введите поизицию от " + 0 + " до " + (getMusicBandCollectionManager().getMusicBandLinkedList().size()));
        } else {
            Random random = new Random();
            request.getRequestBodyMusicBand().getMusicBand().setId(random.nextLong(2, getMAX_ID()));
            request.getRequestBodyMusicBand().getMusicBand().setCreationDate(LocalDate.now().atStartOfDay());
            getMusicBandCollectionManager().getMusicBandLinkedList().add(Integer.parseInt(request.getRequestBody().getArgs()[0]), request.getRequestBodyMusicBand().getMusicBand());
            return new ResponseFactory().createResponse("Элемент успешно добавлен в позицию" + request.getRequestBody().getArgs()[0]);
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
