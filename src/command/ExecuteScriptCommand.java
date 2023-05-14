package command;

import manager.CollectionManager;
import manager.UserManager;
import utility.Printer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contains implementation of execute_script command
 * Read and execute script from file
 *
 * @author ilestegor
 */
public class ExecuteScriptCommand extends Command {
    public ExecuteScriptCommand(CollectionManager collectionManager) {
        super("Команда выполняет скрипт записанный в файле. Принимате путь файла как аргумент.\n " +
                "IMPORTANT: запись команд в файл скрипта идет в столбик сразу с необходимыми аргуменатми для команд", collectionManager);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            String base = System.getenv("ScriptFile");
            String path = base + getArgs();
            File scriptFile = new File(path);
            try {
                if (scriptFile.length() == 0) {
                    printer.printNextLine("Скрипт пустой или такого файла не существует!");
                } else if (scriptFile.canRead()){
                    BufferedReader bf = new BufferedReader(new FileReader(path));
                    String line = bf.readLine();
                    List<String> listOfCommands = new ArrayList<>();
                    while (line != null) {
                            listOfCommands.add(line);
                            line = bf.readLine();
                    }
                    bf.close();
                    UserManager.requestCommandForScript(listOfCommands);
                } else {
                    throw new IOException();
                }
            } catch (IOException ex) {
                printer.printNextLine("Отсутствую права на чтение файла!");
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.printNextLine("Команда execute_script должна принимать аргумент в виде названия файла!");
            return false;
        }
        return true;
    }
}
