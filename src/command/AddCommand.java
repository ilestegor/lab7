package command;

import manager.MusicBandCollection;
import manager.UserManager;
import model.MusicBand;
import utility.Printer;

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
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            MusicBandCollection.addToCollection(UserManager.requestDataForUserMusicBand(new MusicBand()));
            printer.print("Объект успешно добавлен в коллекцию!");
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.print("У команды add нет аргументов!");
            return false;
        }
    }
}
