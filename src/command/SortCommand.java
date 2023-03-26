package command;

import manager.CollectionManager;
import utility.Printer;
import utility.SortById;

/**
 * Class contains implementation of sort command
 * Sorts collection by id
 *
 * @author ilestegor
 */
public class SortCommand extends Command {
    public SortCommand(CollectionManager collectionManager) {
        super("Команда сортирует коллекцию по id", collectionManager);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
                printer.printNextLine("Коллекция пуста! Сортировать нечего");
            } else {
                getMusicBandCollectionManager().getMusicBandLinkedList().sort(new SortById());
                printer.printNextLine("Коллекция успешно отсортирована!");
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.printNextLine("У команды sort не должно быть аргументов!");
            return false;
        }
    }

}
