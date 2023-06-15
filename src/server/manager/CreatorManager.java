package server.manager;

import common.auth.Credential;
import common.auth.RegistrationCode;
import common.auth.User;
import common.exception.NoSuchUserException;
import common.network.Request;
import common.network.Response;
import common.network.ResponseFactory;
import common.utility.passwordHashStrategy.SHA1Login;
import server.MainServerApp;
import server.dao.DAO;
import server.dao.UserDAOImpl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class CreatorManager implements DBUserManager, Serializable {

    private final List<User> userList;
    private final UserDAOImpl userDAO;


    public CreatorManager(UserDAOImpl userDAO) {
        this.userDAO = userDAO;
        this.userList = userDAO.read();
    }

    @Override
    public Response login(Request request) {
        Credential credentials = request.getCredential();
        String pwd = SHA1Login.hashPassword(credentials.getPassword(), userDAO.getSalt(credentials.getLogin()));
        Optional<User> user = userList.stream().filter(targetUser -> targetUser.getCredentials().getPassword().equals(pwd) && targetUser.getCredentials().getLogin().equals(credentials.getLogin())).findFirst();
        if (user.isPresent()) {
            User foundUser = user.get();
            request.setRegistrationCode(RegistrationCode.REGISTERED);
            MainServerApp.LOGGER.info("Пользователь под логином " + request.getCredential().getLogin() + " успешно вошел в аккаунт");
            return new ResponseFactory().createResponse(foundUser.getCredentials().getLogin() + " успешно авторизовался!");
        }
        return new ResponseFactory().createResponse(new NoSuchUserException("Логин или пароль неверные. Попробуйте еще раз").getMessage());
    }

    @Override
    public Response logOut(Request request) {
        Response response = new Response();
        response.setRegistrationCode(RegistrationCode.NOT_REGISTERED);
        response.setMessage("До свидания!");
        return response;
    }

    @Override
    public Response register(Request request) {
        Credential credentials = request.getCredential();
        if (userList.stream().map(User::getCredentials).map(Credential::getLogin).anyMatch(cred -> cred.equals(credentials.getLogin()))) {
            return new ResponseFactory().createResponse("Пользователь с таким логином уже существует. Придумайте другой логин");
        }
        User user = new User(credentials);
        int id = userDAO.create(user);
        if (id == DAO.ERROR) {
            return new ResponseFactory().createResponse("Произошла ошибка при регистрации пользователя");
        }
        user.setId(id);
        this.userList.add(user);
        Response response = new Response();
        MainServerApp.LOGGER.info("Пользователь под именем " + request.getCredential().getLogin() + " зарегистрировал новый аккаунт");
        response.setMessage("Пользователь успешно зарегистрирован");
        response.setRegistrationCode(RegistrationCode.REGISTERED);
        return response;
    }

    @Override
    public boolean checkIfRegistered(Request request) {
        if (request.getCredential() == null) {
            return false;
        }
        String login = request.getCredential().getLogin();
        for (User x : this.userList) {
            if (x.getCredentials().getLogin().equals(login) && request.getRegistrationCode() == RegistrationCode.REGISTERED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkIfLogged(Request request) {
        if (request.getCredential() == null) {
            return false;
        }
        String login = request.getCredential().getLogin();
        String pwd = SHA1Login.hashPassword(request.getCredential().getPassword(), userDAO.getSalt(login));
        for (User x : this.userList) {
            if (x.getCredentials().getLogin().equals(login) && x.getCredentials().getPassword().equals(pwd)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User findUserByCredentials(Credential credential) {
        Optional<User> user = userList.stream().filter(targetUser -> targetUser.getCredentials().getLogin().equals(credential.getLogin())).findFirst();
        return user.orElse(null);
    }
}
