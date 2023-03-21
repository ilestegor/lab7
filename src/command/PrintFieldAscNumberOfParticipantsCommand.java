package command;

import manager.MusicBandCollection;
import model.MusicBand;
import utility.Printer;

import java.util.*;

/**
 * Class contains implementation of print_filed_ascending_number_of_participants
 *
 * @author ilestegor
 */
public class PrintFieldAscNumberOfParticipantsCommand extends Command {

    public PrintFieldAscNumberOfParticipantsCommand(String description, boolean hasArgs) {
        super(description, hasArgs);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            if (MusicBandCollection.getMusicBandLinkedList().isEmpty()) {
                printer.print("Коллекция пуста");
            } else {
                LinkedHashMap<String, Integer> bandNameAndParticipantsCount = new LinkedHashMap<>();
                for (MusicBand musicBand : MusicBandCollection.getMusicBandLinkedList()) {
                    bandNameAndParticipantsCount.put(musicBand.getName(), musicBand.getNumberOfParticipants());
                }
                List<Map.Entry<String, Integer>> newList = new ArrayList<Map.Entry<String, Integer>>(bandNameAndParticipantsCount.entrySet());
                Collections.sort(newList, new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return o1.getValue() - o2.getValue();
                    }
                });
                printer.print("Название группы -> Количество участников:");
                for (Map.Entry<String, Integer> l : newList) {
                    printer.print(l.getKey() + "  ->  " + l.getValue());
                }
            }
        }
    }


    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.print("У команды print_ascending_number_of_participants нет аргументов! Попробуйте еще раз");
            return false;
        }
    }
}
