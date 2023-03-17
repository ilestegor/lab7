package command;

import manager.MusicBandCollection;
import manager.UserManager;
import model.MusicBand;

/**
 * Class contains implementation of add command
 * Adds new element to collection
 *
 * @author ilestegor
 */
public class AddCommand extends Command {
    public AddCommand(String description, boolean hasArgs) {
        super(description, hasArgs);
    }

    @Override
    public void execute() {
        if (checkArgument(getArgs())) {
            MusicBandCollection.addToCollection(UserManager.requestDataForUserMusicBand(new MusicBand()));
        }
    }

    @Override
    public boolean checkArgument(Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            System.out.println("У команды add нет аргументов!");
            return false;
        }
    }
}
