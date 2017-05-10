package com.urthilak.domain;


import java.util.Date;
import java.util.UUID;

public class Quote {

    private String id;
    private String state;
    private String applicationId;
    private String entityId;
    private Date txDate;
    private String message;

    public Quote() {
    }

    public Quote(String id, String state, String applicationId, String entityId, Date txDate, String message) {
        this.id = id;
        this.state = state;
        this.applicationId = applicationId;
        this.entityId = entityId;
        this.txDate = txDate;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Date getTxDate() {
        return txDate;
    }

    public void setTxDate(Date txDate) {
        this.txDate = txDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
