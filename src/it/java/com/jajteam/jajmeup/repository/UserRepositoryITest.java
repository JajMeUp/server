package com.jajteam.jajmeup.repository;

import com.jajteam.jajmeup.AbstractITest;
import com.jajteam.jajmeup.exception.NoSuchUserException;
import com.jajteam.jajmeup.domain.User;
import org.junit.Test;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;

public class UserRepositoryITest extends AbstractITest {

    @Inject
    private UserRepository userRepository;

    @Test(expected = ConstraintViolationException.class)
    public void testCreateMissingRoleShouldFail() {
        User user = new User();
        user.setUsername("test-2");
        user.setPassword("test");
        userRepository.create(user);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateMissingPasswordShouldFail() {
        User user = new User();
        user.setUsername("test-2");
        user.setRole("USER");
        userRepository.create(user);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testCreateMissingUsernameShouldFail() {
        User user = new User();
        user.setPassword("test");
        user.setRole("USER");
        userRepository.create(user);
    }

    @Test
    public void testGetByUsernameSuccess() throws NoSuchUserException {
        User user = new User();
        user.setUsername("user-test");
        user.setPassword("test");
        user.setRole("USER");
        userRepository.create(user);

        user = userRepository.getByUsername("user-test");
        assertThat(user.getId()).isNotNull();
        assertThat(user.getUsername()).isEqualTo("user-test");
        assertThat(user.getRole()).isEqualTo("USER");
        assertThat(user.getCreated()).isNotNull();
    }

    @Test(expected = NoSuchUserException.class)
    public void testGetByUsernameShouldThrowException() throws NoSuchUserException {
        User user = userRepository.getByUsername("test-not-exists");
        fail("Should have thrown a NoSuchUserException");
    }
}
