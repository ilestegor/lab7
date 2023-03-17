package command;

import manager.MusicBandCollection;
import model.MusicBand;

/**
 * Class contains implementation of remove_by_id command
 * Removes element according given id
 *
 * @author ilestegor
 */
public class RemoveByIdCommand extends Command {

    public RemoveByIdCommand(String description, boolean hasArgs) {
        super(description, hasArgs);
    }

    @Override
    public void execute() {
        if (MusicBandCollection.getMusicBandLinkedList().isEmpty()) {
            System.out.println("Коллекция пуста!");
            return;
        }
        if (checkArgument(getArgs())) {
            int id = Integer.parseInt(getArgs().toString());
            for (MusicBand musicBand : MusicBandCollection.getMusicBandLinkedList()) {
                if (musicBand.getId() == id) {
                    MusicBandCollection.getMusicBandLinkedList().remove(musicBand);
                    System.out.println("Элемент с id " + musicBand.getId() + " успешно удален из коллекции!");
                    return;
                }
            }
            System.out.println("Элемент с id " + id + " не найден! Попробуйте еще раз");
        }
    }

    @Override
    public boolean checkArgument(Object inputArgs) {
        if (inputArgs == null) {
            System.out.println("У команды remove_by_id должен быть аргумент (id элемента коллекции)!");
            return false;
        } else {
            try {
                Integer.parseInt(getArgs().toString());
                return true;
            } catch (NumberFormatException ex) {
                System.out.println("Команда remove_by_id имеет аргумент типа данных (int)!");
                return false;
            }
        }
    }
}
