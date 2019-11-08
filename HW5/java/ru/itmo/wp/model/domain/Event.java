package ru.itmo.wp.model.domain;

import java.io.Serializable;

public class Event extends AbstractModel implements Serializable {
    private long userId;
    private Type type;

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public long getUserId() {
        return userId;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        ENTER, LOGOUT
    }
}
