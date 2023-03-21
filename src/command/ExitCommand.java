package command;

import manager.UserManager;
import utility.Printer;

/**
 * Class contains implementation of exit command
 * Terminates the application
 *
 * @author ilestegor
 */
public class ExitCommand extends Command {
    public ExitCommand(String description, boolean hasArgs) {
        super(description, false);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            printer.print("Завершение программы!");
            UserManager.setIsInWork(false);
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.print("Команда exit не имеет аргументов, попробуйте ввести команду без аргументов!");
            return false;
        }
    }


}
