package com.jajteam.jajmeup.mapper;

import com.jajteam.jajmeup.command.UserCommand;
import com.jajteam.jajmeup.command.mapper.UserCommandMapper;
import com.jajteam.jajmeup.domain.User;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class UserCommandMapperTest {

    private final String USERNAME_TEST = "username-test";
    private final String PASSWORD_TEST = "password-test";

    @Test
    public void mapToUserWithDefaultRole() {
        UserCommand command = new UserCommand();
        command.setUsername(USERNAME_TEST);
        command.setPassword(PASSWORD_TEST);

        User user = UserCommandMapper.toUser(command);

        assertThat(user.getUsername()).isEqualTo(USERNAME_TEST);
        assertThat(user.getPassword()).isEqualTo(PASSWORD_TEST);
        assertThat(user.getRole()).isEqualTo(UserCommandMapper.USER_ROLE);
    }
}
