package common.command;

import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import common.utility.SortByName;

/**
 * Class contains implementation of sort command
 * Sorts collection by id
 *
 * @author ilestegor
 */
public class SortCommand extends Command {
    private final RegistrationCode registrationCode;

    public SortCommand(ServerCollectionManager serverCollectionManager, RegistrationCode registrationCode) {
        super("save", "Команда сортирует коллекцию по названию музыкальной группы", serverCollectionManager);
        this.registrationCode = registrationCode;
    }

    public SortCommand(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public Request execute() {
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

    @Override
    public RegistrationCode getRegistrationCode() {
        return registrationCode;
    }
}
