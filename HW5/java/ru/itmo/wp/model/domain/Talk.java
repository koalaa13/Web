package ru.itmo.wp.model.domain;

import java.io.Serializable;

public class Talk extends AbstractModel implements Serializable {
    private long sourceUserId;
    private long targetUserId;

    private String text;

    public void setSourceUserId(long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getSourceUserId() {
        return sourceUserId;
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public String getText() {
        return text;
    }


}
