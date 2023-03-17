package command;

import manager.MusicBandCollection;
import model.MusicBand;

/**
 * Class contains implementation of filter_starts_with_name command
 * Outputs elements which name's start with same substring
 *
 * @author ilestegor
 */
public class FilterStartsWithNameCommand extends Command {

    public FilterStartsWithNameCommand(String description, boolean hasArgs) {
        super(description, hasArgs);
    }

    @Override
    public void execute() {
        int count = 0;
        if (checkArgument(getArgs())) {
            if (MusicBandCollection.getMusicBandLinkedList().isEmpty()) {
                System.out.println("Коллекция пуста!");
            } else {
                for (MusicBand musicBand : MusicBandCollection.getMusicBandLinkedList()) {
                    if (musicBand.getName().startsWith(getArgs().toString())) {
                        count++;
                        System.out.println(musicBand);
                    }
                }
                System.out.println("Количество групп, подходящих под условие " + count);
                if (count == 0) {
                    System.out.println("Групп с такой подстрокой не нашлось!");
                }
            }

        }
    }

    @Override
    public boolean checkArgument(Object inputArgs) {
        if (inputArgs == null) {
            System.out.println("У команды filter_starts_with_name должен быть аргумент!");
            return false;
        } else {
            return true;
        }
    }
}
