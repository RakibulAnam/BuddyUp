package com.example.buddyup.model;

public class OnLocation {
    String recieverId;
    String location;


    public OnLocation(String recieverId, String location) {
        this.recieverId = recieverId;
        this.location = location;
    }


    public OnLocation() {
    }

    public String getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
