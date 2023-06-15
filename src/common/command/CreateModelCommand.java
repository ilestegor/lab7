package common.command;

import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;

import java.io.Serial;
import java.io.Serializable;

public class CreateModelCommand extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = 1453958818680317121L;

    public CreateModelCommand(ServerCollectionManager serverCollectionManager) {
        super("create", "Команда-помощник для создания объекта", serverCollectionManager);
    }

    public CreateModelCommand() {
    }

    @Override
    public Response execute(Request request) {
        int userInputId = Integer.parseInt(request.getRequestBody().getArgs()[0]);
        return getMusicBandCollectionManager().updateElementInCollection(getMusicBandCollectionManager().findModelById(userInputId), userInputId, request);
    }

    @Override
    public Request execute() {
        if (checkArgument(getArgs())) {
            return new RequestFactory().createRequestMusicBandWithArgs(getName(), getArgs(), getUserManager().requestDataForUserMusicBand());
        }
        throw new WrongArgumentException();
    }

    @Override
    public boolean checkArgument(String[] inputArgs) {
        return inputArgs.length != 0;
    }
}
