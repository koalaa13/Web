package ru.itmo.wp.model.repository.impls;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.*;

public class UserRepositoryImpl extends BasicRepositoryImpl<User> implements UserRepository {

    @Override
    public User findByLogin(String login) {
        return findBy("login", login);
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        return findBy("login", login, "passwordSha", passwordSha);
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        return findBy("email", email, "passwordSha", passwordSha);
    }

    @Override
    public User findByLoginOrEmailAndPasswordSha(String loginOrEmail, String passwordSha) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM User WHERE (email=? OR login=?) AND passwordSha=?")) {
                statement.setString(1, loginOrEmail);
                statement.setString(2, loginOrEmail);
                statement.setString(3, passwordSha);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toRepositoryObject(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
    }

    @Override
    public User findByEmail(String email) {
        return findBy("email", email);
    }

    @Override
    protected String getTableName() {
        return "User";
    }

    @Override
    protected User toRepositoryObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "email":
                    user.setEmail(resultSet.getString(i));
                    break;
                default:
                    // No operations.
            }
        }

        return user;
    }

    @Override
    public void save(User user, String passwordSha) {
        save(user, "passwordSha", passwordSha);
    }

    @Override
    public long findUserCount() {
        return findCount();
    }
}
