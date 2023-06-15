package common.command;

import common.auth.RegistrationCode;
import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import server.model.MusicBand;

import java.util.*;

/**
 * Class contains implementation of print_filed_ascending_number_of_participants
 *
 * @author ilestegor
 */
public class PrintFieldAscNumberOfParticipantsCommand extends Command {
    private final RegistrationCode registrationCode;

    public PrintFieldAscNumberOfParticipantsCommand(ServerCollectionManager serverCollectionManager, RegistrationCode registrationCode) {
        super("print_ascending_number_of_participants", "Команда выводит колиечство участников всех групп в порядке возрастания", serverCollectionManager);
        this.registrationCode = registrationCode;
    }


    public PrintFieldAscNumberOfParticipantsCommand(RegistrationCode registrationCode) {
        this.registrationCode = registrationCode;
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs())) {
            return new RequestFactory().createRequest(getName(), getArgs());
        }
        throw new WrongArgumentException("У команды print_ascending_number_of_participants нет аргументов! Попробуйте еще раз");

    }

    @Override
    public Response execute(Request request) {
        if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
            return new ResponseFactory().createResponse("Коллекция пуста");
        } else {
            MultiValuedMap<String, Integer> bandNameAndParticipantsCount = new ArrayListValuedHashMap<>();
            for (MusicBand bands : getMusicBandCollectionManager().getMusicBandLinkedList()) {
                bandNameAndParticipantsCount.put(bands.getName(), bands.getNumberOfParticipants());
            }
            Collection<Map.Entry<String, Integer>> entries = bandNameAndParticipantsCount.entries();
            List<Map.Entry<String, Integer>> newList = new ArrayList<>(entries);
            newList.sort(Comparator.comparingInt(Map.Entry::getValue));
            ArrayList<String> bandsForResponse = new ArrayList<>();
            for (Map.Entry<String, Integer> l : newList) {
                bandsForResponse.add(l.getKey() + "  ->  " + l.getValue());
            }
            return new ResponseFactory().createResponse("Название группы -> Количество участников:\n" + String.join("\n", bandsForResponse));
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
