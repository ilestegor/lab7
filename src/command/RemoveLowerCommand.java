package command;

import manager.MusicBandCollection;
import model.MusicBand;
import utility.Printer;

import java.util.Iterator;

/**
 * Class contains implementation of remove_lower command
 * Removes all elements whoes numberOfParticipants lower than given one
 *
 * @author ilestegor
 */
public class RemoveLowerCommand extends Command {
    public RemoveLowerCommand(String description, boolean hasArgs) {
        super(description, hasArgs);
    }


    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            if (MusicBandCollection.getMusicBandLinkedList().isEmpty()) {
                printer.print("Коллекция пуста!");
            } else {
                if (MusicBandCollection.getIdContainer().contains(Long.parseLong(getArgs().toString()))) {
                    int targetNumber = 0;
                    for (MusicBand m : MusicBandCollection.getMusicBandLinkedList()) {
                        if (m.getId() == Integer.parseInt(getArgs().toString())) {
                            targetNumber = m.getNumberOfParticipants();
                            break;
                        }
                    }
                    Iterator<MusicBand> iterator = MusicBandCollection.getMusicBandLinkedList().iterator();
                    while (iterator.hasNext()) {
                        MusicBand ms = iterator.next();
                        if (targetNumber > ms.getNumberOfParticipants()) {
                            iterator.remove();
                        }
                    }
                    printer.print("Элементы меньшие чем указнный успешно удалены!");
                } else {
                    printer.print("id c номером " + getArgs() + " нет в коллекции");
                }
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.print("У команды remove_lower должны быть аргумент (id элемента)");
            return false;
        } else {
            try {
                Integer.parseInt(inputArgs.toString());
                return true;
            } catch (NumberFormatException ex) {
                printer.print("Аргумент у команды remove_lower должен быть типа (int). Попробуйте еще раз!");
                return false;
            }
        }
    }
}
