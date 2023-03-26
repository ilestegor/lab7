package command;

import manager.CollectionManager;
import model.MusicBand;
import utility.Printer;

/**
 * Class contains implementation of filter_starts_with_name command
 * Outputs elements which name's start with same substring
 *
 * @author ilestegor
 */
public class FilterStartsWithNameCommand extends Command {

    public FilterStartsWithNameCommand(CollectionManager collectionManager) {
        super("Команда выводит элементы, названия групп которых начинаются с заданной подстроки", collectionManager);
    }

    @Override
    public void execute(Printer printer) {
        int count = 0;
        if (checkArgument(new Printer(), getArgs())) {
            if (getMusicBandCollectionManager().getMusicBandLinkedList().isEmpty()) {
                printer.printNextLine("Коллекция пуста!");
            } else {
                for (MusicBand musicBand : getMusicBandCollectionManager().getMusicBandLinkedList()) {
                    if (musicBand.getName().startsWith(getArgs().toString())) {
                        count++;
                        System.out.println(musicBand);
                    }
                }
                printer.printNextLine("Количество групп, подходящих под условие " + count);
                if (count == 0) {
                    printer.printNextLine("Групп с такой подстрокой не нашлось!");
                }
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.printNextLine("У команды filter_starts_with_name должен быть аргумент!");
            return false;
        } else {
            return true;
        }
    }
}
