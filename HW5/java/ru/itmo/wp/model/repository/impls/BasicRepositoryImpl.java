package ru.itmo.wp.model.repository.impls;

import ru.itmo.wp.model.database.DatabaseUtils;
import ru.itmo.wp.model.domain.AbstractModel;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.BasicRepository;

import javax.sql.DataSource;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BasicRepositoryImpl<T extends AbstractModel> implements BasicRepository<T> {
    final DataSource DATA_SOURCE = DatabaseUtils.getDataSource();

    @Override
    public long findCount() {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) FROM " + getTableName())) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    resultSet.next();
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find count of users.", e);
        }
    }

    @Override
    public T find(long id) {
        return findBy("id", id);
    }

    @Override
    public List<T> findAll() {
        List<T> result = new ArrayList<>();
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + getTableName() + " ORDER BY id DESC")) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    T ob;
                    while ((ob = toRepositoryObject(statement.getMetaData(), resultSet)) != null) {
                        result.add(ob);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find User.", e);
        }
        return result;
    }

    @Override
    public T findBy(Object... args) {
        int i = 0;
        List<String> keys = new ArrayList<>();
        List<Object> values = new ArrayList<>();
        for (Object arg : args) {
            if (i % 2 == 0) {
                keys.add(arg.toString());
            } else {
                values.add(arg);
            }
            i++;
        }
        String query = getFindQuery(keys);
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                for (i = 1; i <= values.size(); ++i) {
                    Object curValue = values.get(i - 1);
                    if (curValue instanceof String) {
                        statement.setString(i, (String) curValue);
                    } else if (curValue instanceof Long) {
                        statement.setLong(i, (Long) curValue);
                    } else if (curValue instanceof Date) {
                        statement.setDate(i, (Date) curValue);
                    }
                }
                try (ResultSet resultSet = statement.executeQuery()) {
                    return toRepositoryObject(statement.getMetaData(), resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find data", e);
        }
    }

    @Override
    public void save(T toSave, Object... args) {
        String query = getAddQuery(toSave, args);
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                var fields = toSave.getClass().getDeclaredFields();
                int i = 1;
                for (; i <= fields.length; ++i) {
                    Field field = fields[i - 1];
                    Object curValue = callGetter(toSave, field.getName());
                    if (curValue != null) {
                        if (curValue instanceof String || curValue instanceof Enum) {
                            statement.setString(i, String.valueOf(curValue));
                        } else {
                            statement.setLong(i, Long.parseLong(curValue.toString()));
                        }
                    }
                }
                for (int j = 1; j < args.length; j += 2, ++i) {
                    Object curValue = args[j];
                    if (curValue instanceof String) {
                        statement.setString(i, String.valueOf(curValue));
                    } else {
                        statement.setLong(i, (Long) curValue);
                    }
                }
                if (statement.executeUpdate() != 1) {
                    throw new RepositoryException("Can't save " + toSave.getClass().getName() + ".");
                } else {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        toSave.setId(generatedKeys.getLong(1));
                        toSave.setCreationTime(findBy("id", toSave.getId()).getCreationTime());
                    } else {
                        throw new RepositoryException("Can't save " + toSave.getClass().getName() + "[no autogenerated fields].");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't save " + toSave.getClass().getName() + ".", e);
        }
    }

    private String getAddQuery(T toSave, Object... args) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO `").append(getTableName()).append("` (");
        var fields = toSave.getClass().getDeclaredFields();
        for (Field field : fields) {
            query.append("`").append(field.getName()).append("`, ");
        }
        for (int i = 0; i < args.length; i += 2) {
            query.append("`").append(args[i].toString()).append("`, ");
        }
        query.append("`creationTime`) VALUES (");
        for (int i = 0; i < fields.length + args.length / 2; ++i) {
            query.append("?, ");
        }
        query.append("NOW())");
        return query.toString();
    }

    private Object callGetter(Object obj, String fieldName) {
        PropertyDescriptor pd;
        try {
            pd = new PropertyDescriptor(fieldName, obj.getClass());
            return pd.getReadMethod().invoke(obj);
        } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getFindQuery(List<String> keys) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM `").append(getTableName()).append("` WHERE ");
        for (int i = 0; i < keys.size(); ++i) {
            query.append(keys.get(i)).append("=?");
            if (i != keys.size() - 1) {
                query.append(" AND ");
            }
        }
        return query.toString();
    }

    protected abstract String getTableName();

    protected abstract T toRepositoryObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException;
}
