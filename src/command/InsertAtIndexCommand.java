package command;

import manager.CollectionManager;
import manager.UserManager;
import utility.Printer;

/**
 * Class contains implementation of insert_at command
 * Inserts command at given index
 *
 * @author ilestegor
 */
public class InsertAtIndexCommand extends Command {
    public InsertAtIndexCommand(CollectionManager collectionManager) {
        super("Команда вставляет новый элемент в позицию, равную заданной", collectionManager);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            if (Integer.parseInt(getArgs().toString()) > getMusicBandCollectionManager().getMusicBandLinkedList().size() || Integer.parseInt(getArgs().toString()) < 0) {
                printer.printNextLine("Вы не можете добавить элемент в данную позиция, так как эта позиция выходит за пределы массива " +
                        "Введите поизицию от " + 0 + " до " + (getMusicBandCollectionManager().getMusicBandLinkedList().size()));
            } else {
                getMusicBandCollectionManager().getMusicBandLinkedList().add(Integer.parseInt(getArgs().toString()), UserManager.requestDataForUserMusicBand());
                printer.printNextLine("Элемент успешно добавлен в позицию " + getArgs());
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.printNextLine("У команды insert_at должен быть аргумент (позиция типа (int), в которую вы хотите добавить новый элемент)");
            return false;
        } else {
            try {
                Integer.parseInt(inputArgs.toString());
                return true;
            } catch (NumberFormatException ex) {
                printer.printNextLine("Команда insert_at имеет аргумент типа (int). Попробуйте еще раз!");
                return false;
            }
        }
    }
}
