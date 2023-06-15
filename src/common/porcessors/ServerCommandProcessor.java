package common.porcessors;

import common.command.Command;
import common.manager.CommandManager;
import common.network.Request;
import common.network.Response;

import java.util.HashMap;

public class ServerCommandProcessor {
    private final CommandManager commandManager;

    public ServerCommandProcessor(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public Response processCommand(Request request) {
        if (CommandManager.executeServer(request, commandManager)) {
            HashMap<String, Command> cmd = CommandManager.getCommandsByRegistration(CommandManager.getServerCommandMap(), request);
            cmd.get(request.getCommandDTO().getCommandName()).setName(request.getCommandDTO().getCommandName());
            if (request.getRequestBodyMusicBand() != null) {
                cmd.get(request.getCommandDTO().getCommandName()).setArgs(request.getRequestBodyMusicBand().getMusicBand());
                return cmd.get(request.getCommandDTO().getCommandName()).execute(request);
            }
            cmd.get(request.getCommandDTO().getCommandName()).setArgs(request.getRequestBody().getArgs());
            return cmd.get(request.getCommandDTO().getCommandName()).execute(request);
        }
        return new Response("Такой команды нет или у данной команды не должно быть аргументов!");
    }
}
