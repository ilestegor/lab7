package command;

import manager.CollectionManager;
import utility.Printer;

/**
 * Class contains implementation of info command
 * Outputs basic information about collection (type, date of init, number of elements)
 *
 * @author ilestegor
 */
public class InfoCommand extends Command {
    public InfoCommand(CollectionManager collectionManager) {
        super("Команда выводит основную информацию о коллекции: тип, дата инициализации, количество элементов", collectionManager);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            printer.printNextLine("Тип коллекции: " + getMusicBandCollectionManager().getMusicBandLinkedList().getClass().getSimpleName());
            printer.printNextLine("Дата инициализации: " + getMusicBandCollectionManager().getLocalDate().toString().substring(0, 10));
            printer.printNextLine("Количсетво элементов: " + getMusicBandCollectionManager().getMusicBandLinkedList().size());
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.printNextLine("Команда info не имеет аргументов, попробуйте ввести команду без аргументов!");
            return false;
        }
    }
}
