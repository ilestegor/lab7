package command;

import manager.CollectionManager;
import manager.UserManager;
import utility.Printer;

/**
 * Class contains implementation of exit command
 * Terminates the application
 *
 * @author ilestegor
 */
public class ExitCommand extends Command {
    public ExitCommand(CollectionManager collectionManager) {
        super("Команда завершает программу без сохранения результата в файл", collectionManager);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            printer.printThisLine("Завершение программы!");
            UserManager.setIsInWork(false);
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.printNextLine("Команда exit не имеет аргументов, попробуйте ввести команду без аргументов!");
            return false;
        }
    }

}
