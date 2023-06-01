package common.porcessors;

import common.manager.CommandManager;
import common.network.Request;
import common.network.Response;

public class ServerCommandProcessor {
    public Response processCommand(Request request) {
        if (CommandManager.executeServer(request)) {
            CommandManager.getServerCommandMap().get(request.getCommandDTO().getCommandName()).setName(request.getCommandDTO().getCommandName());
            if (request.getRequestBodyMusicBand() != null) {
                CommandManager.getServerCommandMap().get(request.getCommandDTO().getCommandName()).setArgs(request.getRequestBodyMusicBand().getMusicBand());
                return CommandManager.getServerCommandMap().get(request.getCommandDTO().getCommandName()).execute(request);
            }
            CommandManager.getServerCommandMap().get(request.getCommandDTO().getCommandName()).setArgs(request.getRequestBody().getArgs());
            return CommandManager.getServerCommandMap().get(request.getCommandDTO().getCommandName()).execute(request);
        }
        return new Response("Такой команды нет или у данной команды не должно быть аргументов!");
    }
}
