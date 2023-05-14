package command;

import manager.CollectionManager;
import utility.Printer;

/**
 * Class contains implementation of remove_by_id command
 * Removes element according given id
 *
 * @author ilestegor
 */
public class RemoveByIdCommand extends Command {

    public RemoveByIdCommand(CollectionManager collectionManager) {
        super("Команда удаляет элемент коллекции в соответсвии с его id", collectionManager);
    }

    @Override
    public void execute(Printer printer) {
        if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
            printer.printNextLine("Коллекция пуста!");
        } else {
            if (checkArgument(new Printer(), getArgs())) {
                int id = Integer.parseInt(getArgs().toString());
                getMusicBandCollectionManager().removeFromCollection(getMusicBandCollectionManager().findModelById(id));
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.printNextLine("У команды remove_by_id должен быть аргумент (id элемента коллекции)!");
            return false;
        } else {
            try {
                Integer.parseInt(getArgs().toString());
                return true;
            } catch (NumberFormatException ex) {
                printer.printNextLine("Команда remove_by_id имеет аргумент типа данных (int)!");
                return false;
            }
        }
    }

}
