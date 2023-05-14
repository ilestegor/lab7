package common.manager;

import common.command.*;
import common.network.Request;
import common.utility.Printer;

import java.util.HashMap;

/**
 * Contains methods for storing, getting stuff.command instances
 *
 * @author ilestegor
 */
public class CommandManager {
    private static HashMap<String, Command> clientCommandMap;
    private static HashMap<String, Command> serverCommandMap;
    private final ServerCollectionManager serverCollectionManager;
    private static final Printer printer = new Printer();
    private static final int COMMAND_NAME_POSITION = 0;


    public CommandManager(ServerCollectionManager serverCollectionManager, UserManager userManager) {
        this.serverCollectionManager = serverCollectionManager;
        initServerCommands();
        initClientCommands(userManager);
    }

    private void initServerCommands() {
        serverCommandMap = new HashMap<>();
        serverCommandMap.put("help", new HelpCommand(serverCollectionManager));
        serverCommandMap.put("info", new InfoCommand(serverCollectionManager));
        serverCommandMap.put("exit", new ExitCommand(serverCollectionManager));
        serverCommandMap.put("show", new ShowCommand(serverCollectionManager));
        serverCommandMap.put("clear", new ClearCommand(serverCollectionManager));
        serverCommandMap.put("sort", new SortCommand(serverCollectionManager));
        serverCommandMap.put("print_ascending_number_of_participants", new PrintFieldAscNumberOfParticipantsCommand(serverCollectionManager));
        serverCommandMap.put("remove_by_id", new RemoveByIdCommand(serverCollectionManager));
        serverCommandMap.put("count_by_number_of_participants", new CountByNumberOfParticipantsCommand(serverCollectionManager));
        serverCommandMap.put("filter_starts_with_name", new FilterStartsWithNameCommand(serverCollectionManager));
        serverCommandMap.put("remove_lower", new RemoveLowerCommand(serverCollectionManager));
        serverCommandMap.put("add", new AddCommand(serverCollectionManager));
        serverCommandMap.put("insert_at", new InsertAtIndexCommand(serverCollectionManager));
        serverCommandMap.put("update", new UpdateIdCommand(serverCollectionManager));
        serverCommandMap.put("execute_script", new ExecuteScriptCommand(serverCollectionManager));
    }

    private void initClientCommands(UserManager um) {
        clientCommandMap = new HashMap<>();
        clientCommandMap.put("info", new InfoCommand());
        clientCommandMap.put("help", new HelpCommand());
        clientCommandMap.put("exit", new ExitCommand());
        clientCommandMap.put("clear", new ClearCommand());
        clientCommandMap.put("show", new ShowCommand());
        clientCommandMap.put("print_ascending_number_of_participants", new PrintFieldAscNumberOfParticipantsCommand());
        clientCommandMap.put("sort", new SortCommand());
        clientCommandMap.put("remove_by_id", new RemoveByIdCommand());
        clientCommandMap.put("count_by_number_of_participants", new CountByNumberOfParticipantsCommand());
        clientCommandMap.put("filter_starts_with_name", new FilterStartsWithNameCommand());
        clientCommandMap.put("remove_lower", new RemoveLowerCommand());
        clientCommandMap.put("add", new AddCommand());
        clientCommandMap.put("insert_at", new InsertAtIndexCommand());
        clientCommandMap.put("update", new UpdateIdCommand());
        clientCommandMap.put("execute_script", new ExecuteScriptCommand(um));
    }

    /**
     * Return all commands that stored in HashMap
     *
     * @return HashMap object with Command objects
     */
    public static HashMap<String, Command> getClientCommandMap() {
        return clientCommandMap;
    }

    public static HashMap<String, Command> getServerCommandMap() {
        return serverCommandMap;
    }

    public static boolean executeClient(String[] inputData) {
        if (inputData.length == 0) {
            return false;
        } else {
            String command = inputData[COMMAND_NAME_POSITION];
            return clientCommandMap.containsKey(command);
        }
    }

    public static boolean executeServer(Request request) {
        return serverCommandMap.containsKey(request.getCommandDTO().getCommandName());
    }
}
