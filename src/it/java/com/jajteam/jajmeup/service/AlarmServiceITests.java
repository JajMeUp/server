package com.jajteam.jajmeup.service;

import com.jajteam.jajmeup.AbstractITest;
import com.jajteam.jajmeup.command.AlarmCommand;
import com.jajteam.jajmeup.command.UserCommand;
import com.jajteam.jajmeup.domain.Alarm;
import com.jajteam.jajmeup.domain.User;
import com.jajteam.jajmeup.exception.EntityNotFoundException;
import com.jajteam.jajmeup.exception.InvalidEntityException;
import com.jajteam.jajmeup.exception.UserAlreadyExistException;
import com.jajteam.jajmeup.security.JwtAuthenticationToken;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

public class AlarmServiceITests extends AbstractITest {

    @Inject
    private UserService userService;

    @Inject
    private AlarmService alarmService;

    private User user1;
    private User user2;

    @Before
    public void setUp() throws UserAlreadyExistException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));

        UserCommand command = new UserCommand();
        command.setUsername("username-1");
        command.setPassword("password-test");
        command.setDisplayName("display-name-1");
        user1 = userService.create(command);

        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(user1, authorities));

        command.setUsername("username-2");
        command.setPassword("password-test");
        command.setDisplayName("display-name-1");
        user2 = userService.create(command);
    }

    @Test
    public void createAlarmOk() throws EntityNotFoundException, InvalidEntityException {
        AlarmCommand alarmCommand = new AlarmCommand();
        alarmCommand.setLink("link-test");
        alarmCommand.setMessage("message-test");
        alarmCommand.setTargetId(user2.getProfile().getId());

        Alarm alarm = alarmService.create(alarmCommand);

        assertThat(alarm.getId()).isNotNull();
        assertThat(alarm.getLink()).isEqualTo("link-test");
        assertThat(alarm.getMessage()).isEqualTo("message-test");
        assertThat(alarm.getTarget()).isEqualTo(user2.getProfile());
        assertThat(alarm.getVoter()).isEqualTo(user1.getProfile());
    }

    @Test(expected = EntityNotFoundException.class)
    public void createAlarmThrowsInvalidAlarmException() throws EntityNotFoundException, InvalidEntityException {
        AlarmCommand alarmCommand = new AlarmCommand();
        alarmCommand.setLink("link-test");
        alarmCommand.setMessage("message-test");
        alarmCommand.setTargetId(0L);

        alarmService.create(alarmCommand);

        fail("Should have thrown EntityNotFoundException");
    }
}
