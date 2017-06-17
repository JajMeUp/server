package com.jajteam.jajmeup.validation;

import com.jajteam.jajmeup.domain.User;
import com.jajteam.jajmeup.repository.UserRepository;
import com.jajteam.jajmeup.exception.NoSuchUserException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.inject.Inject;

public class UserValidator implements Validator {

    private UserRepository repository;

    @Inject
    public UserValidator(UserRepository userRepository) {
        this.repository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User)o;
        try {
            repository.getByUsername(user.getUsername());
            errors.rejectValue("username", "error.notUnique.username");
        } catch (NoSuchUserException ignored) {
        }

        // TODO: check for password strength ?
    }
}
