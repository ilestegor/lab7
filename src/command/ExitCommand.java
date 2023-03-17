package command;

import manager.UserManager;

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
    public void execute() {
        if (checkArgument(getArgs())) {
            System.out.println("Завершение программы!");
            UserManager.setIsInWork(false);
        }
    }

    @Override
    public boolean checkArgument(Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            System.out.println("Команда exit не имеет аргументов, попробуйте ввести команду без аргументов!");
            return false;
        }
    }


}
