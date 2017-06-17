package com.jajteam.jajmeup.mapper;

import com.jajteam.jajmeup.command.UserCommand;
import com.jajteam.jajmeup.command.mapper.UserCommandMapper;
import com.jajteam.jajmeup.domain.User;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class UserCommandMapperTest {

    private final String USERNAME_TEST = "username-test";
    private final String PASSWORD_TEST = "password-test";
    private final String ROLE_TEST = "role-test";

    @Test
    public void mapToUserWithDefaultRole() {
        UserCommand command = new UserCommand();
        command.setUsername(USERNAME_TEST);
        command.setPassword(PASSWORD_TEST);

        User user = UserCommandMapper.toUser(command);

        assertThat(user.getUsername()).isEqualTo(USERNAME_TEST);
        assertThat(user.getPassword()).isEqualTo(PASSWORD_TEST);
        assertThat(user.getRole()).isEqualTo(UserCommandMapper.DEFAULT_ROLE);
    }

    @Test
    public void mapToUserWithCustomRole() {
        UserCommand command = new UserCommand();
        command.setUsername(USERNAME_TEST);
        command.setPassword(PASSWORD_TEST);
        command.setRole(ROLE_TEST);

        User user = UserCommandMapper.toUser(command);

        assertThat(user.getUsername()).isEqualTo(USERNAME_TEST);
        assertThat(user.getPassword()).isEqualTo(PASSWORD_TEST);
        assertThat(user.getRole()).isEqualTo(ROLE_TEST);
    }
}
