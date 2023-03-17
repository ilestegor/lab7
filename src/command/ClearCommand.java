package command;

import manager.MusicBandCollection;


/**
 * Class contains implementation of clear command
 * Clears collection
 *
 * @author ilestegor
 */
public class ClearCommand extends Command {
    public ClearCommand(String description, boolean hasArgs) {
        super(description, hasArgs);
    }

    @Override
    public void execute() {
        if (checkArgument(getArgs())) {
            if (MusicBandCollection.getMusicBandLinkedList().isEmpty()) {
                System.out.println("Коллекция уже пустая");
            } else {
                MusicBandCollection.getMusicBandLinkedList().clear();
                System.out.println("Коллекция успешно очищена");
            }
        }
    }

    @Override
    public boolean checkArgument(Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            System.out.println("У команды clear нет аргументов!");
            return false;
        }
    }
}
