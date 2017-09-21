package com.jajteam.jajmeup.command;

import org.hibernate.validator.constraints.NotEmpty;

public class UserCommand {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String displayName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
