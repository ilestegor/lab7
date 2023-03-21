package command;

import manager.MusicBandCollection;
import model.MusicBand;
import utility.Printer;

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
    public void execute(Printer printer) {
        int count = 0;
        if (checkArgument(new Printer(), getArgs())) {
            if (MusicBandCollection.getMusicBandLinkedList().isEmpty()) {
                printer.print("Коллекция пуста!");
            } else {
                for (MusicBand musicBand : MusicBandCollection.getMusicBandLinkedList()) {
                    if (musicBand.getName().startsWith(getArgs().toString())) {
                        count++;
                        System.out.println(musicBand);
                    }
                }
                printer.print("Количество групп, подходящих под условие " + count);
                if (count == 0) {
                    printer.print("Групп с такой подстрокой не нашлось!");
                }
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.print("У команды filter_starts_with_name должен быть аргумент!");
            return false;
        } else {
            return true;
        }
    }
}
