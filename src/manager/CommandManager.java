package manager;

import command.*;

import java.util.HashMap;

/**
 * Contains methods for storing, getting command instances
 * @author ilestegor
 */
public class CommandManager {
    private static final HashMap<String, Command> commandMap;

    static {
        commandMap = new HashMap<>();
        commandMap.put("exit", new ExitCommand("Команда завершает программу без сохранения результата в файл", false));
        commandMap.put("info", new InfoCommand("Команда выводит основную информацию о коллекции: тип, дата инициализации, количество элементов", false));
        commandMap.put("help", new HelpCommand("Команда выводит список достпуных комманд и их описание", false));
        commandMap.put("show", new ShowCommand("Команда выводит все элементы коллекции", false));
        commandMap.put("remove_by_id", new RemoveByIdCommand("Команда удаляет элемент коллекции в соответсвии с его id", true));
        commandMap.put("clear", new ClearCommand("Команда очищает коллекцию", false));
        commandMap.put("add", new AddCommand("Команда добавлет новый пользоватлеьски элемент в коллекцию", false));
        commandMap.put("count_by_number_of_participants", new CountByNumberOfParticipantsCommand("Команда выводит количество элементов, значение поля numberOfParticipants которых равно заданному", true));
        commandMap.put("filter_starts_with_name", new FilterStartsWithNameCommand("Команда выводит элементы, названия групп которых начинаются с заданной подстроки", true));
        commandMap.put("update", new UpdateIdCommand("Команда принимает в видео аргумента id элеменета, которые находится в коллекции и обновляет его данные", true));
        commandMap.put("print_ascending_number_of_participants", new PrintFieldAscNumberOfParticipantsCommand("Команда выводит колиечство участников всех групп в порядке возрастания", false));
        commandMap.put("save", new SaveCommand("Команда сохраняет коолекцию в файл", false));
        commandMap.put("sort", new SortCommand("Команда сортирует коллекцию по id", false));
        commandMap.put("insert_at", new InsertAtIndexCommand("Команда вставляет новый элемент в позицию, равную заданной", true));
        commandMap.put("remove_lower", new RemoveLowerCommand("Команда удаляет элементы меньше чем заданный (по количеству участников)", true));
        commandMap.put("execute_script", new ExecuteScriptCommand("Команда выполняет скрипт записанный в файле. Принимате путь файла как аргумент.\n " +
                "IMPORTANT: запись команд в файл скрипта идет в столбик сразу с необходимыми аргуменатми для команд", true));
    }

    /**
     * Return all commands that stored in HashMap
     * @return HashMap object with Command objects
     */
    public static HashMap<String, Command> getCommandMap() {
        return commandMap;
    }

}
