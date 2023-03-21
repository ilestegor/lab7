package command;

import manager.MusicBandCollection;
import utility.Printer;
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
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            if (MusicBandCollection.getMusicBandLinkedList().isEmpty()){
                printer.print("Коллекция пуста! Сортировать нечего");
            } else {
                MusicBandCollection.getMusicBandLinkedList().sort(new SortById());
                printer.print("Коллекция успешно отсортирована!");
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.print("У команды sort не должно быть аргументов!");
            return false;
        }
    }
}
