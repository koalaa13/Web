package ru.itmo.wp.model.domain;

import java.util.Date;

public abstract class AbstractModel {
    private long id;
    private Date creationTime;

    public void setId(long id) {
        this.id = id;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getId() {
        return id;
    }

    public Date getCreationTime() {
        return creationTime;
    }

}
