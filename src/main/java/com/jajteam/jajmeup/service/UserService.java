package com.jajteam.jajmeup.service;

import com.jajteam.jajmeup.command.UserCommand;
import com.jajteam.jajmeup.command.mapper.UserCommandMapper;
import com.jajteam.jajmeup.domain.User;
import com.jajteam.jajmeup.dto.TokenDto;
import com.jajteam.jajmeup.exception.InvalidCredentialException;
import com.jajteam.jajmeup.exception.NoSuchUserException;
import com.jajteam.jajmeup.exception.UserAlreadyExistException;
import com.jajteam.jajmeup.repository.UserRepository;
import com.jajteam.jajmeup.validation.UserValidator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import javax.inject.Inject;

public class UserService {

    private UserRepository repository;
    private UserValidator validator;

    @Inject
    public UserService(UserRepository repository, UserValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Transactional
    public User getByUsername(String username) throws NoSuchUserException {
        return repository.getByUsername(username);
    }

    @Transactional
    public User create(UserCommand command) throws UserAlreadyExistException {
        User user = UserCommandMapper.toUser(command);
        BindingResult results = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, results);

        if(results.hasErrors()) {
            throw new UserAlreadyExistException();
        }

        repository.create(user);
        return user;
    }

    @Transactional
    public TokenDto authenticate(String username, String password) throws InvalidCredentialException {
        final String tokenString = repository.authenticate(username, password);

        TokenDto token = new TokenDto();
        token.setToken(tokenString);

        return token;
    }
}
