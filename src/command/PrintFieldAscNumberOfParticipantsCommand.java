package command;

import manager.MusicBandCollection;
import model.MusicBand;

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
    public void execute() {
        if (checkArgument(getArgs())) {
            if (MusicBandCollection.getMusicBandLinkedList().isEmpty()) {
                System.out.println("Коллекция пуста");
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


                System.out.println("Название группы -> Количество участников:");
                for (Map.Entry<String, Integer> l : newList) {
                    System.out.println(l.getKey() + "  ->  " + l.getValue());

                }
            }
        }
    }


    @Override
    public boolean checkArgument(Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            System.out.println("У команды print_ascending_number_of_participants нет аргументов! Попробуйте еще раз");
            return false;
        }
    }
}
