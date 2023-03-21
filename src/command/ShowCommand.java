package command;

import manager.MusicBandCollection;
import model.MusicBand;
import utility.Printer;

/**
 * Class contains implementation of show command
 * Outputs all elements in collection
 *
 * @author ilestegor
 */
public class ShowCommand extends Command {
    public ShowCommand(String description, boolean hasArgs) {
        super(description, false);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            if (MusicBandCollection.getMusicBandLinkedList().isEmpty()) {
                printer.print("Коллекция пуста!");
            } else {
                for (MusicBand musicBand : MusicBandCollection.getMusicBandLinkedList()) {
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
            printer.print("Команда show не имеет аргументов, попробуйте ввести команду без аршументов!");
            return false;
        }
    }
}
