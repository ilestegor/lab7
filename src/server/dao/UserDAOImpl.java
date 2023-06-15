package server.dao;

import common.auth.Credential;
import common.auth.User;
import server.MainServerApp;
import server.utility.Queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class UserDAOImpl implements DAO<User> {
    private final Connection connection;
    private static final int LOGIN_INDEX = 1;
    private static final int PASSWORD_INDEX = 2;
    private static final int SALT_INDEX = 3;


    public UserDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int create(User user) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.CREATE_USER.getQuery())) {
            preparedStatement.setString(LOGIN_INDEX, user.getCredentials().getLogin());
            preparedStatement.setString(PASSWORD_INDEX, user.getCredentials().getPassword());
            preparedStatement.setString(SALT_INDEX, user.getCredentials().getSalt());
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            int id = result.getInt("id");
            preparedStatement.close();
            return id;
        } catch (SQLException e) {
            MainServerApp.LOGGER.info("Пользователь не добавлен в БД");
            return ERROR;
        }
    }

    @Override
    public int update(User user) {
        final int idIndex = 3;
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.UPDATE_USER.getQuery())) {
            preparedStatement.setString(LOGIN_INDEX, user.getCredentials().getLogin());
            preparedStatement.setString(PASSWORD_INDEX, user.getCredentials().getPassword());
            preparedStatement.setInt(idIndex, user.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            return ERROR;
        }
    }

    //for further expansion
    @Override
    public int deleteAll(User user) {
        return 0;
    }

    @Override
    public int deleteById(User user) {
        final int idIndex = 1;
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.DELETE_USER.getQuery())) {
            preparedStatement.setInt(idIndex, user.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return ERROR;
        }
    }

    @Override
    public List<User> read() {
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.READ_USER.getQuery())) {
            List<User> userList = new ArrayList<>();
            preparedStatement.executeQuery();
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String login = result.getString("login");
                String password = result.getString("password");
                userList.add(new User(id, new Credential(login, password)));
            }
            return userList;
        } catch (SQLException ex) {
            return new LinkedList<>();
        }
    }

    public String getSalt(String login) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.GET_SALT.getQuery())) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String salt = resultSet.getString("salt");
            preparedStatement.close();
            return salt;
        } catch (SQLException ex) {
            return "";
        }
    }
}
