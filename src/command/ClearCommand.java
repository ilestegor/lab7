package command;

import manager.MusicBandCollection;
import utility.Printer;


/**
 * Class contains implementation of clear command
 * Clears collection
 *
 * @author ilestegor
 */
public class ClearCommand extends Command {
    public ClearCommand(String description, boolean hasArgs) {
        super(description, hasArgs);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            if (MusicBandCollection.getMusicBandLinkedList().isEmpty()) {
                printer.print("Коллекция уже пустая");
            } else {
                MusicBandCollection.getMusicBandLinkedList().clear();
                printer.print("Коллекция очищена!");
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.print("У команды clear нет аргументов!");
            return false;
        }
    }
}
