package com.jajteam.jajmeup.dto;

public class AlarmDto {

    private String voterName;
    private String URL;
    private String message;

    public String getVoterName() { return voterName; }

    public String getURL() {
        return URL;
    }

    public String getMessage() { return message; }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setMessage(String message) { this.message = message; }

    public void setVoterName(String name) { this.voterName = name; }
}