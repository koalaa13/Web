package ru.itmo.wp.model.repository.impls;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.EventRepository;

import java.sql.*;

public class EventRepositoryImpl extends BasicRepositoryImpl<Event> implements EventRepository {

    @Override
    protected String getTableName() {
        return "Event";
    }

    @Override
    protected Event toRepositoryObject(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }
        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    event.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    event.setUserId(resultSet.getLong(i));
                    break;
                case "creationTime":
                    event.setCreationTime(resultSet.getTimestamp(i));
                    break;
                case "type":
                    event.setType(Event.Type.valueOf(resultSet.getString(i)));
                    break;
                default:
                    // No operations.
            }
        }
        return event;
    }
}
