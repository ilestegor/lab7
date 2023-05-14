package common.command;

import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import common.utility.Printer;
import common.utility.SortByName;

/**
 * Class contains implementation of sort command
 * Sorts collection by id
 *
 * @author ilestegor
 */
public class SortCommand extends Command {
    public SortCommand(ServerCollectionManager serverCollectionManager) {
        super("save", "Команда сортирует коллекцию по названию музыкальной группы", serverCollectionManager);
    }

    public SortCommand() {
    }

    @Override
    public Request execute(Printer printer) {
        if (checkArgument(getArgs())) {
            return new RequestFactory().createRequest(getName(), getArgs());
        }
        throw new WrongArgumentException("У команды sort не должно быть аргументов!");

    }

    @Override
    public Response execute(Request request) {
        if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
            return new ResponseFactory().createResponse("Коллекция пуста! Сортировать нечего");
        } else {
            getMusicBandCollectionManager().getMusicBandLinkedList().sort(new SortByName());
            return new ResponseFactory().createResponse("Коллекция успешно отсортирована!");
        }
    }

    @Override
    public boolean checkArgument(String[] inputArgs) {
        return inputArgs.length == 0;
    }
}
