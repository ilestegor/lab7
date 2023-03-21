package command;

import manager.MusicBandCollection;
import manager.UserManager;
import model.MusicBand;
import utility.Printer;

/**
 * Class contains implementation of insert_at command
 * Inserts command at given index
 *
 * @author ilestegor
 */
public class InsertAtIndexCommand extends Command {
    public InsertAtIndexCommand(String description, boolean hasArgs) {
        super(description, hasArgs);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            if (Integer.parseInt(getArgs().toString()) > MusicBandCollection.getMusicBandLinkedList().size() || Integer.parseInt(getArgs().toString()) < 0) {
                printer.print("Вы не можете добавить элемент в данную позиция, так как эта позиция выходит за пределы массива " +
                        "Введите поизицию от " + 0 + " до " + (MusicBandCollection.getMusicBandLinkedList().size()));
            } else {
                MusicBandCollection.getMusicBandLinkedList().add(Integer.parseInt(getArgs().toString()), UserManager.requestDataForUserMusicBand(new MusicBand()));
                printer.print("Элемент успешно добавлен в позицию " + getArgs());
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.print("У команды insert_at должен быть аргумент (позиция типа (int), в которую вы хотите добавить новый элемент)");
            return false;
        } else {
            try {
                Integer.parseInt(inputArgs.toString());
                return true;
            } catch (NumberFormatException ex) {
                printer.print("Команда insert_at имеет аргумент типа (int). Попробуйте еще раз!");
                return false;
            }
        }
    }
}
