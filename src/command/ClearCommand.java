package command;

import manager.CollectionManager;
import utility.Printer;


/**
 * Class contains implementation of clear command
 * Clears collection
 *
 * @author ilestegor
 */
public class ClearCommand extends Command {

    public ClearCommand(CollectionManager collectionManager) {
        super("Команда очищает коллекцию", collectionManager);

    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
                printer.printNextLine("Коллекция уже пустая");
            } else {
                getMusicBandCollectionManager().clearCollection();
                printer.printNextLine("Коллекция очищена!");
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.printNextLine("У команды clear нет аргументов!");
            return false;
        }
    }

}
