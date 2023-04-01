package command;

import manager.CollectionManager;
import manager.UserManager;
import utility.Printer;

/**
 * Class contains implementation of add command
 * Adds new element to collection
 *
 * @author ilestegor
 */
public class AddCommand extends Command {
    public AddCommand(CollectionManager collectionManager) {
        super("Команда добавляет новый пользоватлеьский элемент в коллекцию", collectionManager);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            if (getMusicBandCollectionManager().getMusicBandLinkedList().add(UserManager.requestDataForUserMusicBand())){
                printer.printNextLine("Объект успешно добавлен в коллекцию!");
            } else printer.printNextLine("Объект не добавлен в коллекцию! Попробуйте еще раз");
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.printNextLine("У команды add нет аргументов!");
            return false;
        }
    }
}
