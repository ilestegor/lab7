package command;

import manager.MusicBandCollection;
import manager.UserManager;
import model.MusicBand;

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
    public void execute() {
        int count = 0;
        if (MusicBandCollection.getMusicBandLinkedList().isEmpty()) {
            System.out.println("Коллекция пуста!");
        } else {
            if (checkArgument(getArgs())) {
                for (MusicBand musicBand : MusicBandCollection.getMusicBandLinkedList()) {
                    if (musicBand.getId() == Long.parseLong(getArgs().toString())) {
                        count++;
                        musicBand.updateElement(UserManager.requestDataForUserMusicBand(new MusicBand()));
                    }
                }
                if (count == 0) {
                    System.out.println("Элемента с id " + getArgs() + " не найдено!");
                }
            }
        }
    }

    @Override
    public boolean checkArgument(Object inputArgs) {
        if (inputArgs == null) {
            System.out.println("У команды update должен быть аргумент id (id элемента, значения которого вы хотите обновить). Попробуйте еще раз!");
            return false;
        } else {
            try {
                Integer.parseInt(getArgs().toString());
                return true;
            } catch (NumberFormatException ex) {
                System.out.println("Команда update имеет аргумент типа (int). Попробуйте еще раз!");
                return false;
            }
        }
    }
}
