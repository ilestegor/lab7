package common.manager;

import common.auth.RegistrationCode;
import common.command.*;
import common.network.Request;
import server.manager.CreatorManager;
import server.manager.DBUserManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains methods for storing, getting stuff.command instances
 *
 * @author ilestegor
 */
public class CommandManager {
    private static HashMap<String, Command> clientCommandMap;
    private static HashMap<String, Command> serverCommandMap;
    private static HashMap<String, Command> clientHelperCommandMap;
    private static HashMap<String, Command> serverHelperCommandMap;
    private final ServerCollectionManager serverCollectionManager;
    private DBUserManager dbUserManager;
    private CreatorManager creatorManager;
    private static final int COMMAND_NAME_POSITION = 0;


    public CommandManager(ServerCollectionManager serverCollectionManager, DBUserManager dbUserManager, CreatorManager creatorManager) {
        this.dbUserManager = dbUserManager;
        this.serverCollectionManager = serverCollectionManager;
        this.creatorManager = creatorManager;
        initServerCommands();
        initServerHelperCommands();
    }

    public CommandManager(ServerCollectionManager serverCollectionManager, UserManager userManager) {
        this.serverCollectionManager = serverCollectionManager;
        initClientCommands(userManager);
        initClientHelperCommands();
    }


    private void initServerCommands() {
        serverCommandMap = new HashMap<>();
        serverCommandMap.put("logout", new LogOutCommand(serverCollectionManager, dbUserManager, RegistrationCode.REGISTERED));
        serverCommandMap.put("login", new LoginCommand(serverCollectionManager, dbUserManager, RegistrationCode.NOT_REGISTERED));
        serverCommandMap.put("register", new RegisterCommand(serverCollectionManager, dbUserManager, RegistrationCode.NOT_REGISTERED));
        serverCommandMap.put("help", new HelpCommand(serverCollectionManager, RegistrationCode.FOR_BOTH));
        serverCommandMap.put("info", new InfoCommand(serverCollectionManager, RegistrationCode.REGISTERED));
        serverCommandMap.put("exit", new ExitCommand(serverCollectionManager, RegistrationCode.FOR_BOTH));
        serverCommandMap.put("show", new ShowCommand(serverCollectionManager, RegistrationCode.REGISTERED));
        serverCommandMap.put("clear", new ClearCommand(serverCollectionManager, RegistrationCode.REGISTERED));
        serverCommandMap.put("sort", new SortCommand(serverCollectionManager, RegistrationCode.REGISTERED));
        serverCommandMap.put("print_ascending_number_of_participants", new PrintFieldAscNumberOfParticipantsCommand(serverCollectionManager, RegistrationCode.REGISTERED));
        serverCommandMap.put("remove_by_id", new RemoveByIdCommand(serverCollectionManager, RegistrationCode.REGISTERED));
        serverCommandMap.put("count_by_number_of_participants", new CountByNumberOfParticipantsCommand(serverCollectionManager, RegistrationCode.REGISTERED));
        serverCommandMap.put("filter_starts_with_name", new FilterStartsWithNameCommand(serverCollectionManager, RegistrationCode.REGISTERED));
        serverCommandMap.put("remove_lower", new RemoveLowerCommand(serverCollectionManager, RegistrationCode.REGISTERED));
        serverCommandMap.put("add", new AddCommand(serverCollectionManager, RegistrationCode.REGISTERED, creatorManager));
        serverCommandMap.put("insert_at", new InsertAtIndexCommand(serverCollectionManager, RegistrationCode.REGISTERED));
        serverCommandMap.put("update", new UpdateIdCommand(serverCollectionManager, RegistrationCode.REGISTERED));
        serverCommandMap.put("execute_script", new ExecuteScriptCommand(serverCollectionManager, RegistrationCode.REGISTERED));
    }

    private void initClientCommands(UserManager um) {
        clientCommandMap = new HashMap<>();
        clientCommandMap.put("register", new RegisterCommand(RegistrationCode.NOT_REGISTERED));
        clientCommandMap.put("logout", new LogOutCommand(serverCollectionManager, dbUserManager, RegistrationCode.REGISTERED));
        clientCommandMap.put("login", new LoginCommand(RegistrationCode.NOT_REGISTERED));
        clientCommandMap.put("info", new InfoCommand(RegistrationCode.REGISTERED));
        clientCommandMap.put("help", new HelpCommand(RegistrationCode.FOR_BOTH));
        clientCommandMap.put("exit", new ExitCommand(RegistrationCode.FOR_BOTH));
        clientCommandMap.put("clear", new ClearCommand(RegistrationCode.REGISTERED));
        clientCommandMap.put("show", new ShowCommand(RegistrationCode.REGISTERED));
        clientCommandMap.put("print_ascending_number_of_participants", new PrintFieldAscNumberOfParticipantsCommand(RegistrationCode.REGISTERED));
        clientCommandMap.put("sort", new SortCommand(RegistrationCode.REGISTERED));
        clientCommandMap.put("remove_by_id", new RemoveByIdCommand(RegistrationCode.REGISTERED));
        clientCommandMap.put("count_by_number_of_participants", new CountByNumberOfParticipantsCommand(RegistrationCode.REGISTERED));
        clientCommandMap.put("filter_starts_with_name", new FilterStartsWithNameCommand(RegistrationCode.REGISTERED));
        clientCommandMap.put("remove_lower", new RemoveLowerCommand(RegistrationCode.REGISTERED));
        clientCommandMap.put("add", new AddCommand(RegistrationCode.REGISTERED));
        clientCommandMap.put("insert_at", new InsertAtIndexCommand(RegistrationCode.REGISTERED));
        clientCommandMap.put("update", new UpdateIdCommand(RegistrationCode.REGISTERED));
        clientCommandMap.put("execute_script", new ExecuteScriptCommand(um, RegistrationCode.REGISTERED));
    }

    private void initServerHelperCommands() {
        serverHelperCommandMap = new HashMap<>();
        serverHelperCommandMap.put("create", new CreateModelCommand(serverCollectionManager));
        serverHelperCommandMap.put("insert", new InsertAtCreationCommand(serverCollectionManager, creatorManager));
    }

    private void initClientHelperCommands() {
        clientHelperCommandMap = new HashMap<>();
        clientHelperCommandMap.put("create", new CreateModelCommand());
        clientHelperCommandMap.put("insert", new InsertAtCreationCommand());
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

    public static HashMap<String, Command> getClientHelperCommandMap() {
        return clientHelperCommandMap;
    }

    public static HashMap<String, Command> getServerHelperCommandMap() {
        return serverHelperCommandMap;
    }

    public static HashMap<String, Command> getCommandsByRegistration(HashMap<String, Command> commandHashMap, Request request) {
        HashMap<String, Command> result = new HashMap<>();
        for (Map.Entry<String, Command> x : commandHashMap.entrySet()) {
            if (x.getValue().getRegistrationCode().equals(request.getRegistrationCode()) || x.getValue().getRegistrationCode().equals(RegistrationCode.FOR_BOTH)) {
                result.put(x.getKey(), x.getValue());
            }
        }
        return result;
    }


    public static boolean executeClient(String[] inputData, Request request) {
        if (inputData.length == 0) {
            return false;
        } else {
            String command = inputData[COMMAND_NAME_POSITION];
            HashMap<String, Command> cmd = getCommandsByRegistration(clientCommandMap, request);
            return cmd.containsKey(command);
        }
    }

    public static boolean executeServer(Request request) {
        HashMap<String, Command> cmd = getCommandsByRegistration(serverCommandMap, request);
        return cmd.containsKey(request.getCommandDTO().getCommandName());
    }

}
