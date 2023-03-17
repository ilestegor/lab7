package command;

import manager.MusicBandCollection;
import model.MusicBand;

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
    public void execute() {
        if (checkArgument(getArgs())) {
            if (MusicBandCollection.getMusicBandLinkedList().isEmpty()) {
                System.out.println("Коллекция пуста!");
            } else {
                for (MusicBand musicBand : MusicBandCollection.getMusicBandLinkedList()) {
                    System.out.println(musicBand);
                }
            }
        }
    }

    @Override
    public boolean checkArgument(Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            System.out.println("Команда show не имеет аргументов, попробуйте ввести команду без аршументов!");
            return false;
        }
    }
}
