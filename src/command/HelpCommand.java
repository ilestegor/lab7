package command;

import manager.CollectionManager;
import manager.CommandManager;
import utility.Printer;

import java.util.Map;

/**
 * Class contains implementation of help command\
 * Outputs list of available commands
 *
 * @author ilestegor
 */
public class HelpCommand extends Command {
    public HelpCommand(CollectionManager collectionManager) {
        super("Команда выводит список достпуных комманд и их описание", collectionManager);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            int count = 1;
            for (Map.Entry<String, Command> command : CommandManager.getCommandMap().entrySet()) {
                printer.printNextLine(count + ". " + command.getKey() + " - " + command.getValue().getDescription());
                count++;
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.printNextLine("У команды help нет аргументов, попробуйте еще раз!");
            return false;
        }
    }
}
