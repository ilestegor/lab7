package manager;

import command.Command;
import command.ExitCommand;
import command.InfoCommand;

import java.util.HashMap;

public class CommandManager {
    private static MusicBandCollection musicBandCollection;
    private static final HashMap<String, Command> commandMap;

    static {
        commandMap = new HashMap<>();
        commandMap.put("exit", new ExitCommand("Завершение программы", "Команда завершает программу без сохранения результата в файл", false));
        commandMap.put("info", new InfoCommand("Вывод информации о коллекции", "Команда выводит основную информацию о коллекции: тип, дата инициализации, количество элементов", false));
    }

    public static HashMap<String, Command> getCommandMap(){
        return commandMap;
    }

}
