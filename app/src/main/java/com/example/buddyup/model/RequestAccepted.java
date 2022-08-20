package com.example.buddyup.model;

public class RequestAccepted {
    public String acceptorId;
    public String senderId;

    public RequestAccepted(String acceptorId, String senderId) {
        this.acceptorId = acceptorId;
        this.senderId = senderId;
    }

    public RequestAccepted() {
    }

    public String getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(String acceptorId) {
        this.acceptorId = acceptorId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
