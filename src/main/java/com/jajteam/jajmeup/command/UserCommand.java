package com.jajteam.jajmeup.command;

import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nullable;

public class UserCommand {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @Nullable
    private String role;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
