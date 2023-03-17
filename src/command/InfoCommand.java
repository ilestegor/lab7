package command;

import manager.MusicBandCollection;

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
    public void execute() {
        if (checkArgument(getArgs())) {
            System.out.println("Тип коллекции: " + MusicBandCollection.getMusicBandLinkedList().getClass().getSimpleName());
            System.out.println("Дата инициализации: " + MusicBandCollection.getLocalDate().toString().substring(0, 10));
            System.out.println("Количсетво элементов: " + MusicBandCollection.getMusicBandLinkedList().size());
        }
    }

    @Override
    public boolean checkArgument(Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            System.out.println("Команда info не имеет аргументов, попробуйте ввести команду без аргументов!");
            return false;
        }
    }
}
