package command;

import manager.MusicBandCollection;
import model.MusicBand;
import utility.Printer;

/**
 * Class contains implementation of count_by_number_of_participants command
 * Counts by number of participants and outputs amount of elements with the same number of participants
 *
 * @author ilestegor
 */
public class CountByNumberOfParticipantsCommand extends Command {

    public CountByNumberOfParticipantsCommand(String description, boolean hasArgs) {
        super(description, hasArgs);
    }

    @Override
    public void execute(Printer printer) {
        int count = 0;
        if (checkArgument(new Printer(), getArgs())) {
            if (MusicBandCollection.getMusicBandLinkedList().isEmpty()) {
                printer.print("Коллекция пуста!");
            } else {
                for (MusicBand musicBand : MusicBandCollection.getMusicBandLinkedList()) {
                    if (musicBand.getNumberOfParticipants() == Integer.parseInt(getArgs().toString())) {
                        count++;
                    }
                }
                if (count == 0) {
                    printer.print("Музыкальных групп с таким количеством участников не найдено!");
                } else printer.print("Количество музыкальных групп с указанным количеством участников: " + count);
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            System.out.println("У команды count_by_number_of_participants должен быть аргумент!");
            return false;
        } else {
            try {
                int count = Integer.parseInt(getArgs().toString());
                if (count < 0) {
                    printer.print("Аргумент должен быть > 0!");
                    return false;
                } else {
                    return true;
                }
            } catch (NumberFormatException ex) {
                printer.print("Команда count_by_number_of_participants имеет аргумент типа  (int)!");
                return false;
            }
        }
    }
}
