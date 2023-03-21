package command;

import manager.MusicBandCollection;
import model.MusicBand;
import utility.Printer;

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
    public void execute(Printer printer) {
        if (MusicBandCollection.getMusicBandLinkedList().isEmpty()) {
            printer.print("Коллекция пуста!");
        } else {
            if (checkArgument(new Printer(), getArgs())) {
                int id = Integer.parseInt(getArgs().toString());
                for (MusicBand musicBand : MusicBandCollection.getMusicBandLinkedList()) {
                    if (musicBand.getId() == id) {
                        MusicBandCollection.getMusicBandLinkedList().remove(musicBand);
                        printer.print("Элемент с id " + musicBand.getId() + " успешно удален из коллекции!");
                        break;
                    }
                }
                printer.print("Элемент с id " + id + " не найден! Попробуйте еще раз");
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.print("У команды remove_by_id должен быть аргумент (id элемента коллекции)!");
            return false;
        } else {
            try {
                Integer.parseInt(getArgs().toString());
                return true;
            } catch (NumberFormatException ex) {
                printer.print("Команда remove_by_id имеет аргумент типа данных (int)!");
                return false;
            }
        }
    }
}
