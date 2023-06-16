package common.command;

import common.exception.WrongArgumentException;
import common.manager.ServerCollectionManager;
import common.network.Request;
import common.network.RequestFactory;
import common.network.Response;
import common.network.ResponseFactory;
import server.manager.CreatorManager;

import java.io.Serial;
import java.io.Serializable;

public class InsertAtCreationCommand extends Command implements Serializable {
    @Serial
    private static final long serialVersionUID = -5199754166715380570L;
    private CreatorManager creatorManager;

    public InsertAtCreationCommand(ServerCollectionManager serverCollectionManager, CreatorManager creatorManager) {
        super("insert", "Команда-помощник для создания объект при insert", serverCollectionManager);
        this.creatorManager = creatorManager;
    }

    public InsertAtCreationCommand() {
    }

    @Override
    public Response execute(Request request) {
            request.getRequestBodyMusicBand().getMusicBand().setCreatorId(creatorManager.findUserByCredentials(request.getCredential()).getId());
            request.getRequestBodyMusicBand().getMusicBand().setUserName(creatorManager.findUserById(request.getRequestBodyMusicBand().getMusicBand().getCreatorId()).getCredentials().getLogin());
            getMusicBandCollectionManager().insertAtIndex(request.getRequestBodyMusicBand().getMusicBand(), Integer.parseInt(request.getRequestBody().getArgs()[0]));
            return new ResponseFactory().createResponse("Элемент успешно добавлен в позицию" + request.getRequestBody().getArgs()[0]);
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
