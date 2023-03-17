package command;

import manager.CommandManager;

import java.util.Map;

/**
 * Class contains implementation of help command\
 * Outputs list of available commands
 *
 * @author ilestegor
 */
public class HelpCommand extends Command {
    public HelpCommand(String description, boolean hasArgs) {
        super(description, false);
    }

    @Override
    public void execute() {
        if (checkArgument(getArgs())) {
            int count = 1;
            for (Map.Entry<String, Command> command : CommandManager.getCommandMap().entrySet()) {
                System.out.println(count++ + ". " + command.getKey() + " " + command.getValue().getDescription());
            }
        }
    }

    @Override
    public boolean checkArgument(Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            System.out.println("У команды help нет аргументов, попробуйте еще раз!");
            return false;
        }
    }
}
