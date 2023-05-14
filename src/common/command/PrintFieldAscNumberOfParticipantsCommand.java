package common.command;

import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import common.utility.Printer;
import server.model.MusicBand;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * Class contains implementation of print_filed_ascending_number_of_participants
 *
 * @author ilestegor
 */
public class PrintFieldAscNumberOfParticipantsCommand extends Command {

    public PrintFieldAscNumberOfParticipantsCommand(ServerCollectionManager serverCollectionManager) {
        super("print_ascending_number_of_participants", "Команда выводит колиечство участников всех групп в порядке возрастания", serverCollectionManager);
    }

    public PrintFieldAscNumberOfParticipantsCommand() {
    }

    @Override
    public Request execute(Printer printer) {
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
            LinkedHashMap<String, Integer> bandNameAndParticipantsCount =  getMusicBandCollectionManager().getMusicBandLinkedList().stream().collect(Collectors.toMap(MusicBand::getName, MusicBand::getNumberOfParticipants, (v1, v2) -> v2, LinkedHashMap::new)); //from list to linkedhashmap
            List<Map.Entry<String, Integer>> newList = new ArrayList<>(bandNameAndParticipantsCount.entrySet());
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
}
