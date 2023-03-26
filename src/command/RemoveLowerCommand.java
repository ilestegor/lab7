package command;

import manager.CollectionManager;
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
    public RemoveLowerCommand(CollectionManager collectionManager) {
        super("Команда удаляет элементы меньше чем заданный (по количеству участников)", collectionManager);
    }


    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
                printer.printNextLine("Коллекция пуста!");
            } else {
                if (getMusicBandCollectionManager().getMusicBandLinkedList().contains(Long.parseLong(getArgs().toString()))) {
                    int targetNumber = 0;
                    for (MusicBand m : getMusicBandCollectionManager().getMusicBandLinkedList()) {
                        if (m.getId() == Integer.parseInt(getArgs().toString())) {
                            targetNumber = m.getNumberOfParticipants();
                            break;
                        }
                    }
                    Iterator<MusicBand> iterator = getMusicBandCollectionManager().getMusicBandLinkedList().iterator();
                    while (iterator.hasNext()) {
                        MusicBand ms = iterator.next();
                        if (targetNumber > ms.getNumberOfParticipants()) {
                            iterator.remove();
                        }
                    }
                    printer.printNextLine("Элементы меньшие чем указнный успешно удалены!");
                } else {
                    printer.printNextLine("id c номером " + getArgs() + " нет в коллекции");
                }
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.printNextLine("У команды remove_lower должны быть аргумент (id элемента)");
            return false;
        } else {
            try {
                Integer.parseInt(inputArgs.toString());
                return true;
            } catch (NumberFormatException ex) {
                printer.printNextLine("Аргумент у команды remove_lower должен быть типа (int). Попробуйте еще раз!");
                return false;
            }
        }
    }
}
