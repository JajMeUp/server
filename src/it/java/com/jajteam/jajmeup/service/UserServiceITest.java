package com.jajteam.jajmeup.service;

import com.jajteam.jajmeup.AbstractITest;
import com.jajteam.jajmeup.command.UserCommand;
import com.jajteam.jajmeup.domain.User;
import com.jajteam.jajmeup.domain.Profile;
import com.jajteam.jajmeup.dto.TokenDto;
import com.jajteam.jajmeup.exception.InvalidCredentialException;
import com.jajteam.jajmeup.exception.NoSuchUserException;
import com.jajteam.jajmeup.exception.UserAlreadyExistException;
import com.jajteam.jajmeup.properties.JajSecurityProperties;
import com.jajteam.jajmeup.security.TokenUtils;
import io.jsonwebtoken.Claims;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

public class UserServiceITest extends AbstractITest {

    @Inject
    private UserService userService;
    @Inject
    private PasswordEncoder passwordEncoder;
    @Inject
    private JajSecurityProperties jajSecurityProperties;

    private UserCommand getTestUserCommand() {
        UserCommand command = new UserCommand();
        command.setUsername("user-test");
        command.setPassword("test");
        command.setDisplayName("display-name-test");

        return command;
    }

    @Test
    public void testCreateSuccess() throws UserAlreadyExistException {
        final UserCommand command = getTestUserCommand();
        User user = userService.create(command);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getUsername()).isEqualTo("user-test");
        assertThat(passwordEncoder.matches("test", user.getPassword())).isTrue();
        assertThat(user.getRole()).isEqualTo("USER");
        assertThat(user.getCreated()).isNotNull();
        assertThat(user.getSettings()).isNotNull();

        Profile profile = user.getSettings();
        assertThat(profile.getDisplayName()).isEqualTo("display-name-test");
        assertThat(profile.getVisibility()).isEqualTo(Profile.VISIBILITY_FRIENDS);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void testCreateShouldThrowUserAlreadyExistsException() throws UserAlreadyExistException {
        userService.create(getTestUserCommand());
        userService.create(getTestUserCommand());
        fail("Should have thrown UserAlreadyExistException");
    }

    @Test
    public void testAuthenticateSuccess() throws UserAlreadyExistException, InvalidCredentialException, NoSuchUserException {
        User user = userService.create(getTestUserCommand());

        TokenDto token = userService.authenticate(user.getUsername(), getTestUserCommand().getPassword());
        Claims claims = TokenUtils.parseClaims(token.getToken(), jajSecurityProperties.getSecretKey());

        assertThat(jajSecurityProperties.getSecretKey()).isEqualTo("secret-key-test");
        assertThat(claims.getSubject()).isEqualTo(user.getUsername());
        assertThat(claims.get("role")).isEqualTo(user.getRole());
    }

    @Test(expected = InvalidCredentialException.class)
    public void testAuthenticationFailed() throws InvalidCredentialException, NoSuchUserException, UserAlreadyExistException {
        userService.create(getTestUserCommand());

        userService.authenticate("user-test", "wrong-password");
        fail("Should have thrown InvalidCredentialException");
    }
}
