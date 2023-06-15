package common.command;

import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;

/**
 * Class contains implementation of filter_starts_with_name stuff.command
 * Outputs elements which name's start with same substring
 *
 * @author ilestegor
 */
public class FilterStartsWithNameCommand extends Command {
    private final RegistrationCode registrationCode;

    public FilterStartsWithNameCommand(ServerCollectionManager serverCollectionManager, RegistrationCode registrationCode) {
        super("filter_starts_with_name", "Команда выводит элементы, названия групп которых начинаются с заданной подстроки", serverCollectionManager);
        this.registrationCode = registrationCode;
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs()))
            return new RequestFactory().createRequest(getName(), getArgs());
        throw new WrongArgumentException("У команды filter_starts_with_name должен быть аргумент (подстрока, с которой начинается название музыкальной группы) \n" +
                "IMPORTANT: Регистр подстроки важен! Если название начинается с большой буквы, а Вы ввели маленькую или наоборот, то музыкальная группа найдена не будет");
    }

    public FilterStartsWithNameCommand(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public Response execute(Request request) {
        if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
            return new ResponseFactory().createResponse("Коллекция пуста!");
        } else {
            long count = getMusicBandCollectionManager().getMusicBandLinkedList().stream().filter(musicBand -> musicBand.getName().startsWith(request.getRequestBody().getArgs()[0])).count();
            if (count == 0) {
                return new ResponseFactory().createResponse("Групп с такой подстрокой не нашлось!");
            }
            return new ResponseFactory().createResponse("Количество групп, подходящих под условие - " + count);
        }

    }

    @Override
    public boolean checkArgument(String[] inputArgs) {
        return inputArgs.length != 0;
    }

    @Override
    public RegistrationCode getRegistrationCode() {
        return registrationCode;
    }
}
