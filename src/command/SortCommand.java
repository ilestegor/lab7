package command;

import manager.MusicBandCollection;
import utility.SortById;

/**
 * Class contains implementation of sort command
 * Sorts collection by id
 *
 * @author ilestegor
 */
public class SortCommand extends Command {
    public SortCommand(String description, boolean hasArgs) {
        super(description, hasArgs);
    }

    @Override
    public void execute() {
        if (checkArgument(getArgs())) {
            MusicBandCollection.getMusicBandLinkedList().sort(new SortById());
            System.out.println("Коллекция успешно отсортирована!");
        }
    }

    @Override
    public boolean checkArgument(Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            System.out.println("У команды sort не должно быть аргументов!");
            return false;
        }
    }
}
