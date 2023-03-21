package command;

import manager.MusicBandCollection;
import utility.Printer;

/**
 * Class contains implementation of info command
 * Outputs basic information about collection (type, date of init, number of elements)
 *
 * @author ilestegor
 */
public class InfoCommand extends Command {
    public InfoCommand(String description, boolean hasArgs) {
        super(description, false);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            printer.print("Тип коллекции: " + MusicBandCollection.getMusicBandLinkedList().getClass().getSimpleName());
            printer.print("Дата инициализации: " + MusicBandCollection.getLocalDate().toString().substring(0, 10));
            printer.print("Количсетво элементов: " + MusicBandCollection.getMusicBandLinkedList().size());
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.print("Команда info не имеет аргументов, попробуйте ввести команду без аргументов!");
            return false;
        }
    }
}
