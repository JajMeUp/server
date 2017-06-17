package com.jajteam.jajmeup.command.mapper;

import com.jajteam.jajmeup.command.UserCommand;
import com.jajteam.jajmeup.domain.User;
import org.springframework.util.StringUtils;

public class UserCommandMapper {

    public static final String DEFAULT_ROLE = "USER";

    public static User toUser(UserCommand command) {
        User user = new User();
        user.setUsername(command.getUsername());
        user.setPassword(command.getPassword());

        String role = DEFAULT_ROLE;
        if(StringUtils.hasText(command.getRole())) {
            role = command.getRole();
        }
        user.setRole(role);

        return user;
    }
}
