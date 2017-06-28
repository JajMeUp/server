package com.jajteam.jajmeup.service;

import com.jajteam.jajmeup.AbstractITest;
import com.jajteam.jajmeup.command.UserCommand;
import com.jajteam.jajmeup.domain.Friendship;
import com.jajteam.jajmeup.domain.User;
import com.jajteam.jajmeup.exception.InvalidEntityException;
import com.jajteam.jajmeup.exception.UserAlreadyExistException;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class FriendshipServiceITest extends AbstractITest {

    @Inject
    private FriendshipService friendshipService;

    @Inject
    private UserService userService;

    private User user1;
    private User user2;

    @Before
    public void setUp() throws UserAlreadyExistException {
        UserCommand command = new UserCommand();
        command.setUsername("username-1");
        command.setPassword("password-test");
        command.setDisplayName("display-name-1");
        user1 = userService.create(command);

        command.setUsername("username-2");
        command.setPassword("password-test");
        command.setDisplayName("display-name-1");
        user2 = userService.create(command);
    }

    @Test
    public void createFriendshipOk() throws InvalidEntityException {
        Friendship friendship = friendshipService.create(user1.getProfile().getId(), user2.getProfile().getId());

        assertThat(friendship.getId()).isNotNull();
        assertThat(friendship.getRequester()).isEqualTo(user1.getProfile());
        assertThat(friendship.getTarget()).isEqualTo(user2.getProfile());
        assertThat(friendship.getStatus()).isEqualTo(Friendship.PENDING);
        assertThat(friendship.getUpdated()).isNotNull();
    }
}
