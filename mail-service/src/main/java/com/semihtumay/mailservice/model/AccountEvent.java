package com.semihtumay.mailservice.model;

import java.util.UUID;

public class AccountEvent {
    private String id;
    private String username;
    private String mail;
    private String password;
    private String mailStatus;
    private long createdDate;

    public UUID getId() {
        return id == null ? null : UUID.fromString(id); // UUID'ye dönüştür
    }

    public void setId(UUID id) {
        this.id = id == null ? null : id.toString(); // UUID'den String'e dönüştür
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(String mailStatus) {
        this.mailStatus = mailStatus;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }
}
