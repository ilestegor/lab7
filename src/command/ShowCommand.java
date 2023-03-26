package command;

import manager.CollectionManager;
import model.MusicBand;
import utility.Printer;

/**
 * Class contains implementation of show command
 * Outputs all elements in collection
 *
 * @author ilestegor
 */
public class ShowCommand extends Command {
    public ShowCommand(CollectionManager collectionManager) {
        super("Команда выводит все элементы коллекции", collectionManager);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
                printer.printNextLine("Коллекция пуста!");
            } else {
                for (MusicBand musicBand : getMusicBandCollectionManager().getMusicBandLinkedList()) {
                    System.out.println(musicBand);
                }
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.printNextLine("Команда show не имеет аргументов, попробуйте ввести команду без аршументов!");
            return false;
        }
    }
}
