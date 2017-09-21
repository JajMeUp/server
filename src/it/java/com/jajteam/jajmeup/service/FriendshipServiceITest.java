package com.jajteam.jajmeup.service;

import com.jajteam.jajmeup.AbstractITest;
import com.jajteam.jajmeup.command.UserCommand;
import com.jajteam.jajmeup.domain.Friendship;
import com.jajteam.jajmeup.domain.User;
import com.jajteam.jajmeup.exception.*;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

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
        authenticateUser(user1);

        command.setUsername("username-2");
        command.setPassword("password-test");
        command.setDisplayName("display-name-1");
        user2 = userService.create(command);
    }

    @Test
    public void createFriendshipOk() throws InvalidEntityException {
        Friendship friendship = friendshipService.create(user2.getProfile().getId());

        assertThat(friendship.getId()).isNotNull();
        assertThat(friendship.getRequester()).isEqualTo(user1.getProfile());
        assertThat(friendship.getTarget()).isEqualTo(user2.getProfile());
        assertThat(friendship.getStatus()).isEqualTo(Friendship.PENDING);
        assertThat(friendship.getUpdated()).isNotNull();
    }

    @Test(expected = InvalidEntityException.class)
    public void createFriendshipThrowsInvalidEntityException() throws InvalidEntityException {
        friendshipService.create(0L);
        fail("Should have thrown InvalidEntityException because the target is not a valid profile");
    }

    @Test(expected = InvalidEntityException.class)
    public void createFriendshipThrowsInvalidEntityExceptionBecauseSameTargetAndRequester() throws InvalidEntityException {
        friendshipService.create(user1.getProfile().getId());
        fail("Should have thrown InvalidEntityException because requester and target are the same profile");
    }

    @Test(expected = InvalidEntityException.class)
    public void createFriendshipThrowsInvalidEntityExceptionBecauseFriendshipAlreadyExists() throws InvalidEntityException {
        friendshipService.create(user2.getProfile().getId());
        friendshipService.create(user2.getProfile().getId());
        fail("Should have thrown InvalidEntityException because this friendship already exists");
    }

    @Test(expected = InvalidEntityException.class)
    public void createFriendshipThrowsInvalidEntityExceptionBecauseFriendshipAlreadyExistsBis() throws InvalidEntityException {
        friendshipService.create(user2.getProfile().getId());

        authenticateUser(user2);
        friendshipService.create(user1.getProfile().getId());
        fail("Should have thrown InvalidEntityException because this friendship already exists");
    }

    @Test
    public void getPendingFriendshipRequests() throws InvalidEntityException {
        friendshipService.create(user2.getProfile().getId());

        authenticateUser(user1);
        assertThat(friendshipService.getPendingRequests()).hasSize(0);
        authenticateUser(user2);
        assertThat(friendshipService.getPendingRequests()).hasSize(1);
    }

    @Test
    public void answerFriendRequestSuccess() throws InvalidEntityException, EntityNotFoundException, ProtectedResourceException, FriendshipAlreadyAnsweredException {
        Friendship friendship = friendshipService.create(user2.getProfile().getId());

        authenticateUser(user2);
        friendshipService.answerFriendRequest(friendship.getId(), Friendship.ACCEPTED);

        assertThat(friendship.getStatus()).isEqualTo(Friendship.ACCEPTED);
        assertThat(friendship.getUpdated()).isCloseTo(Date.from(Instant.now()), 100);
    }

    @Test(expected = EntityNotFoundException.class)
    public void answerFriendRequestFailsBecauseItDoesNotExist() throws EntityNotFoundException, ProtectedResourceException, FriendshipAlreadyAnsweredException {
        friendshipService.answerFriendRequest(0L, Friendship.ACCEPTED);
        fail("Should have thrown EntityNotFoundException");
    }

    @Test(expected = ProtectedResourceException.class)
    public void answerFriendRequestFailsBecauseAccessIsDenied() throws InvalidEntityException, EntityNotFoundException, ProtectedResourceException, FriendshipAlreadyAnsweredException {
        Friendship friendship = friendshipService.create(user2.getProfile().getId());

        // User1 is logged, but he can't answer this friendship
        // because user2 is the target (only user2 can answer this friendship)
        friendshipService.answerFriendRequest(friendship.getId(), Friendship.ACCEPTED);
        fail("Should have thrown ProtectedResourceException because user1 does NOT have access to this friendship");
    }

    @Test(expected = FriendshipAlreadyAnsweredException.class)
    public void answerFriendRequestFailsBecauseItIsNotPending() throws InvalidEntityException, EntityNotFoundException, ProtectedResourceException, FriendshipAlreadyAnsweredException {
        Friendship friendship = friendshipService.create(user2.getProfile().getId());

        authenticateUser(user2);
        friendshipService.answerFriendRequest(friendship.getId(), Friendship.ACCEPTED);
        friendshipService.answerFriendRequest(friendship.getId(), Friendship.ACCEPTED);
        fail("Should have thrown FriendshipAlreadyAnsweredException because this friendship is not PENDING");
    }
}
