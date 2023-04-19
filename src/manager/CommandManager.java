package manager;

import command.*;

import java.util.HashMap;

/**
 * Contains methods for storing, getting command instances
 *
 * @author ilestegor
 */
public class CommandManager {
    private static HashMap<String, Command> commandMap;
    private final CollectionManager collectionManager;

    public CommandManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        initCommands();
    }

    void initCommands() {
        commandMap = new HashMap<>();
        commandMap.put("exit", new ExitCommand(collectionManager));
        commandMap.put("info", new InfoCommand(collectionManager));
        commandMap.put("help", new HelpCommand(collectionManager));
        commandMap.put("show", new ShowCommand(collectionManager));
        commandMap.put("remove_by_id", new RemoveByIdCommand(collectionManager));
        commandMap.put("clear", new ClearCommand(collectionManager));
        commandMap.put("add", new AddCommand(collectionManager));
        commandMap.put("count_by_number_of_participants", new CountByNumberOfParticipantsCommand(collectionManager));
        commandMap.put("filter_starts_with_name", new FilterStartsWithNameCommand(collectionManager));
        commandMap.put("update", new UpdateIdCommand(collectionManager));
        commandMap.put("print_ascending_number_of_participants", new PrintFieldAscNumberOfParticipantsCommand(collectionManager));
        commandMap.put("save", new SaveCommand(collectionManager));
        commandMap.put("sort", new SortCommand(collectionManager));
        commandMap.put("insert_at", new InsertAtIndexCommand(collectionManager));
        commandMap.put("remove_lower", new RemoveLowerCommand(collectionManager));
        commandMap.put("execute_script", new ExecuteScriptCommand(collectionManager));
    }

    /**
     * Return all commands that stored in HashMap
     *
     * @return HashMap object with Command objects
     */
    public static HashMap<String, Command> getCommandMap() {
        return commandMap;
    }

}
