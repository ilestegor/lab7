package server.manager;

import common.auth.Credential;
import common.auth.User;
import common.network.Request;
import common.network.Response;

public interface DBUserManager {
    Response login(Request request);

    Response register(Request request);

    Response logOut(Request request);

    boolean checkIfRegistered(Request request);

    boolean checkIfLogged(Request request);

    User findUserByCredentials(Credential credential);
}
