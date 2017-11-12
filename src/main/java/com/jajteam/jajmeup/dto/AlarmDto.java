package com.jajteam.jajmeup.dto;

public class AlarmDto {

    private String voterName;
    private String link;
    private String message;

    public String getVoterName() { return voterName; }

    public String getLink() {
        return link;
    }

    public String getMessage() { return message; }

    public void setLink(String URL) {
        this.link = URL;
    }

    public void setMessage(String message) { this.message = message; }

    public void setVoterName(String name) { this.voterName = name; }
}