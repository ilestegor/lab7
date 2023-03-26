package command;

import manager.CollectionManager;
import model.MusicBand;
import utility.Printer;

import java.util.*;

/**
 * Class contains implementation of print_filed_ascending_number_of_participants
 *
 * @author ilestegor
 */
public class PrintFieldAscNumberOfParticipantsCommand extends Command {

    public PrintFieldAscNumberOfParticipantsCommand(CollectionManager collectionManager) {
        super("Команда выводит колиечство участников всех групп в порядке возрастания", collectionManager);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
                printer.printNextLine("Коллекция пуста");
            } else {
                LinkedHashMap<String, Integer> bandNameAndParticipantsCount = new LinkedHashMap<>();
                for (MusicBand musicBand : getMusicBandCollectionManager().getMusicBandLinkedList()) {
                    bandNameAndParticipantsCount.put(musicBand.getName(), musicBand.getNumberOfParticipants());
                }
                List<Map.Entry<String, Integer>> newList = new ArrayList<Map.Entry<String, Integer>>(bandNameAndParticipantsCount.entrySet());
                Collections.sort(newList, new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return o1.getValue() - o2.getValue();
                    }
                });
                printer.printNextLine("Название группы -> Количество участников:");
                for (Map.Entry<String, Integer> l : newList) {
                    printer.printNextLine(l.getKey() + "  ->  " + l.getValue());
                }
            }
        }
    }


    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.printNextLine("У команды print_ascending_number_of_participants нет аргументов! Попробуйте еще раз");
            return false;
        }
    }
}
