package common.command;

import common.auth.RegistrationCode;
import common.exception.RecursionException;
import common.manager.ServerCollectionManager;
import common.manager.UserManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import common.utility.Printer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Class contains implementation of execute_script stuff.command
 * Read and execute script from file
 *
 * @author ilestegor
 */
public class ExecuteScriptCommand extends Command {
    private UserManager userManager;
    private final HashSet<File> filePaths = new HashSet<>();
    private final Printer printer = new Printer();
    private final RegistrationCode registrationCode;

    public ExecuteScriptCommand(ServerCollectionManager serverCollectionManager, RegistrationCode registrationCode) {
        super("execute_script", "Команда выполняет скрипт записанный в файле. Принимате путь файла как аргумент.\n " +
                "IMPORTANT: запись команд в файл скрипта идет в столбик сразу с необходимыми аргуменатми для команд", serverCollectionManager);
        this.registrationCode = registrationCode;
    }

    public ExecuteScriptCommand(UserManager userManager, RegistrationCode registrationCode) {
        this.userManager = userManager;
        this.registrationCode = registrationCode;
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs())) {
            String path = getArgs()[0];
            File scriptFile = new File(path).getAbsoluteFile();
            if (filePaths.add(scriptFile)) {
                try {
                    if (scriptFile.length() == 0) {
                        printer.printNextLine("Скрипт пустой или такого файла не существует!");
                    } else if (scriptFile.canRead()) {
                        BufferedReader bf = new BufferedReader(new FileReader(path));
                        String line = bf.readLine();
                        LinkedList<String> listOfCommands = new LinkedList<>();
                        while (line != null) {
                            listOfCommands.add(line);
                            line = bf.readLine();
                        }
                        bf.close();
                        userManager.setInputCommand(listOfCommands);
                    } else {
                        throw new IOException();
                    }
                } catch (IOException ex) {
                    printer.printNextLine("Отсутствую права на чтение файла!");
                }
            } else {
                filePaths.clear();
                throw new RecursionException("\nСкрипт вызывает сам себя! Выход из скрипта");
            }
        }
        return new RequestFactory().createRequest(getName(), getArgs());
    }

    @Override
    public Response execute(Request request) {
        return new ResponseFactory().createResponse("Выполняется скрипт");
    }

    @Override
    public boolean checkArgument(String[] inputArgs) {
        Printer printer = new Printer();
        if (inputArgs.length == 0) {
            printer.printNextLine("Команда execute_script должна принимать аргумент в виде названия файла!");
            return false;
        }
        return true;
    }

    @Override
    public RegistrationCode getRegistrationCode() {
        return registrationCode;
    }
}
