package common.command;

import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;

/**
 * Class contains implementation of info stuff.command
 * Outputs basic information about collection (type, date of init, number of elements)
 *
 * @author ilestegor
 */
public class InfoCommand extends Command {
    private final RegistrationCode registrationCode;

    public InfoCommand(ServerCollectionManager serverCollectionManager, RegistrationCode registrationCode) {
        super("info", "Команда выводит основную информацию о коллекции: тип, дата инициализации, количество элементов", serverCollectionManager);
        this.registrationCode = registrationCode;
    }

    public InfoCommand(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs()))
            return new RequestFactory().createRequest(getName(), getArgs());
        throw new WrongArgumentException("Команда info не имеет аргументов, попробуйте ввести команду без аргументов!");
    }

    @Override
    public Response execute(Request request) {
        return new Response("Тип коллекции: " + getMusicBandCollectionManager().getMusicBandLinkedList().getClass().getSimpleName() + "\n" +
                "Дата инициализации: " + getMusicBandCollectionManager().getLocalDate().toString().substring(0, 10) + "\n" +
                "Количсетво элементов: " + getMusicBandCollectionManager().getMusicBandLinkedList().size());

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
