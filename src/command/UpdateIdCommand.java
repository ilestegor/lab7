package command;

import manager.CollectionManager;
import manager.UserManager;
import model.MusicBand;
import utility.Printer;

/**
 * Class contains implementation of update command
 * Updates element's data according id
 *
 * @author ilestegor
 */
public class UpdateIdCommand extends Command {

    public UpdateIdCommand(CollectionManager collectionManager) {
        super("Команда принимает в виде аргумента id элеменета, который находится в коллекции и обновляет его данные", collectionManager);
    }

    @Override
    public void execute(Printer printer) {
        if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
            printer.printNextLine("Коллекция пуста!");
        } else {
            if (checkArgument(new Printer(), getArgs())) {
                int userInputId = Integer.parseInt(getArgs().toString());
                getMusicBandCollectionManager().updateElementInCollection(getMusicBandCollectionManager().findModelById(userInputId), userInputId);
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.printNextLine("У команды update должен быть аргумент id (id элемента, значение которого вы хотите обновить). Попробуйте еще раз!");
            return false;
        } else {
            try {
                Integer.parseInt(getArgs().toString());
                return true;
            } catch (NumberFormatException ex) {
                printer.printNextLine("Команда update имеет аргумент типа (int). Попробуйте еще раз!");
                return false;
            }
        }
    }
}
