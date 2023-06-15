package common.command;


import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;

/**
 * Class contains implementation of count_by_number_of_participants stuff.command
 * Counts by number of participants and outputs amount of elements with the same number of participants
 *
 * @author ilestegor
 */
public class CountByNumberOfParticipantsCommand extends Command {

    private final RegistrationCode registrationCode;

    public CountByNumberOfParticipantsCommand(ServerCollectionManager serverCollectionManager, RegistrationCode registrationCode) {
        super("count_by_number_of_participants", "Команда выводит количество элементов, значение поля numberOfParticipants которых равно заданному", serverCollectionManager);
        this.registrationCode = registrationCode;
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs()))
            return new RequestFactory().createRequest(getName(), getArgs());
        throw new WrongArgumentException("У команды count_by_number_of_participants должен быть аргумент > 0 типа (int)");
    }

    public CountByNumberOfParticipantsCommand(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public Response execute(Request request) {
//        int count = 0;
        int userCountInput = Integer.parseInt(request.getRequestBody().getArgs()[0]);
        if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
            return new ResponseFactory().createResponse("Коллекция пуста!");
        } else {
            long count = getMusicBandCollectionManager().getMusicBandLinkedList().stream().filter(musicBand -> musicBand.getNumberOfParticipants().equals(userCountInput)).count();
            if (count == 0) {
                return new ResponseFactory().createResponse("Музыкальных групп с таким количеством участников не найдено!");
            }
            return new ResponseFactory().createResponse("Количество музыкальных групп с указанным количеством участников: " + count);
        }
    }

    @Override
    public boolean checkArgument(String[] inputArgs) {
        if (inputArgs.length == 0) {
            return false;
        } else {
            try {
                int count = Integer.parseInt(getArgs()[0]);
                return count >= 0;
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
