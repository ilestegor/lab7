package command;

import manager.MusicBandCollection;
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

    public UpdateIdCommand(String description, boolean hasArgs) {
        super(description, hasArgs);
    }

    @Override
    public void execute(Printer printer) {
        int count = 0;
        if (MusicBandCollection.getMusicBandLinkedList().isEmpty()) {
            printer.print("Коллекция пуста!");
        } else {
            if (checkArgument(new Printer(), getArgs())) {
                for (MusicBand musicBand : MusicBandCollection.getMusicBandLinkedList()) {
                    if (musicBand.getId() == Long.parseLong(getArgs().toString())) {
                        count++;
                        musicBand.updateElement(UserManager.requestDataForUserMusicBand(new MusicBand()));
                    }
                }
                if (count == 0) {
                    printer.print("Элемента с id " + getArgs() + " не найдено!");
                }
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.print("У команды update должен быть аргумент id (id элемента, значения которого вы хотите обновить). Попробуйте еще раз!");
            return false;
        } else {
            try {
                Integer.parseInt(getArgs().toString());
                return true;
            } catch (NumberFormatException ex) {
                printer.print("Команда update имеет аргумент типа (int). Попробуйте еще раз!");
                return false;
            }
        }
    }
}
