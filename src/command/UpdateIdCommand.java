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
        super("Команда принимает в видео аргумента id элеменета, которые находится в коллекции и обновляет его данные", collectionManager);
    }

    @Override
    public void execute(Printer printer) {
        int count = 0;
        if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
            printer.printNextLine("Коллекция пуста!");
        } else {
            if (checkArgument(new Printer(), getArgs())) {
                for (MusicBand musicBand : getMusicBandCollectionManager().getMusicBandLinkedList()) {
                    if (musicBand.getId() == Long.parseLong(getArgs().toString())) {
                        count++;
                        musicBand.updateElement(UserManager.requestDataForUserMusicBand());
                    }
                }
                if (count == 0) {
                    printer.printNextLine("Элемента с id " + getArgs() + " не найдено!");
                }
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.printNextLine("У команды update должен быть аргумент id (id элемента, значения которого вы хотите обновить). Попробуйте еще раз!");
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
