package com.example.buddyup.model;

public class HangoutRequest {
    String interest;
    String location;
    String id;

    public HangoutRequest(String interest, String location, String id) {
        this.interest = interest;
        this.location = location;
        this.id = id;
    }

    public HangoutRequest() {
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
