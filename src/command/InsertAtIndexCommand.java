package command;

import manager.MusicBandCollection;
import manager.UserManager;
import model.MusicBand;

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
    public void execute() {
        if (checkArgument(getArgs())) {
            if (Integer.parseInt(getArgs().toString()) > MusicBandCollection.getMusicBandLinkedList().size() || Integer.parseInt(getArgs().toString()) < 0) {
                System.out.println("Вы не можете добавить элемент в данную позиция, так как эта позиция выходит за пределы массива " +
                        "Введите поизицию от " + 0 + " до " + (MusicBandCollection.getMusicBandLinkedList().size()));
            } else {
                MusicBandCollection.getMusicBandLinkedList().add(Integer.parseInt(getArgs().toString()), UserManager.requestDataForUserMusicBand(new MusicBand()));
                System.out.println("Элемент успешно добавлен в позицию " + getArgs());
            }
        }
    }

    @Override
    public boolean checkArgument(Object inputArgs) {
        if (inputArgs == null) {
            System.out.println("У команды insert_at должен быть аргумент (позиция типа (int), в которую вы хотите добавить новый элемент)");
            return false;
        } else {
            try {
                Integer.parseInt(inputArgs.toString());
                return true;
            } catch (NumberFormatException ex) {
                System.out.println("Команда insert_at имеет аргумент типа (int). Попробуйте еще раз!");
                return false;
            }
        }
    }
}
