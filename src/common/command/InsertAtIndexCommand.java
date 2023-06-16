package common.command;

import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import server.manager.CreatorManager;

import java.time.LocalDate;
import java.util.Random;

/**
 * Class contains implementation of insert_at stuff.command
 * Inserts stuff.command at given index
 *
 * @author ilestegor
 */
public class InsertAtIndexCommand extends Command {
    private final RegistrationCode registrationCode;

    public InsertAtIndexCommand(ServerCollectionManager serverCollectionManager, RegistrationCode registrationCode) {
        super("insert_at", "Команда вставляет новый элемент в позицию, равную заданной", serverCollectionManager);
        this.registrationCode = registrationCode;
    }

    public InsertAtIndexCommand(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs())) {
            return new RequestFactory().createRequest(getName(), getArgs());
        }
        throw new WrongArgumentException("У команды insert_at должен быть аргумент (позиция типа (int), в которую вы хотите добавить новый элемент)");

    }

    @Override
    public Response execute(Request request) {
        if (Integer.parseInt(request.getRequestBody().getArgs()[0]) > getMusicBandCollectionManager().getMusicBandLinkedList().size() || Integer.parseInt(request.getRequestBody().getArgs()[0]) < 0) {
            return new ResponseFactory().createResponseWithMessage("Вы не можете добавить элемент в данную позиция, так как эта позиция выходит за пределы массива " +
                    "Введите поизицию от " + 0 + " до " + (getMusicBandCollectionManager().getMusicBandLinkedList().size()), false, "insert");
        } else {
            return new ResponseFactory().createResponseWithNoMessage(true, "insert");
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

    @Override
    public RegistrationCode getRegistrationCode() {
        return registrationCode;
    }
}
