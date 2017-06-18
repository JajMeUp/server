package com.jajteam.jajmeup.command.mapper;

import com.jajteam.jajmeup.command.UserCommand;
import com.jajteam.jajmeup.domain.User;

public class UserCommandMapper {

    public static final String USER_ROLE = "USER";

    public static User toUser(UserCommand command) {
        User user = new User();
        user.setUsername(command.getUsername());
        user.setPassword(command.getPassword());
        user.setRole(USER_ROLE);

        return user;
    }
}
