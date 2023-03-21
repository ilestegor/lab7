package command;

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
    public ExecuteScriptCommand(String description, boolean hasArgs) {
        super(description, hasArgs);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            String base = "/Users/ilestegor/Desktop/Универ/1курс/2сем/прога/student-6/data/";
            String path = base + getArgs();
            try {
                if (new File(path).length() == 0) {
                    printer.print("Скрипт пустой!");
                } else {
                    BufferedReader bf = new BufferedReader(new FileReader(path));
                    String line = bf.readLine();
                    List<String> listOfCommands = new ArrayList<>();
                    while (line != null) {
                        listOfCommands.add(line);
                        line = bf.readLine();
                    }
                    bf.close();
                    UserManager.requestCommandForScript(listOfCommands);
                }
            } catch (IOException ex) {
                printer.print("Такого файла не существует! Проверьте, что файл находится в папке");
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.print("Команда execute_script должна принимать аргумент в виде пути к файлу!");
            return false;
        }
        return true;
    }
}
