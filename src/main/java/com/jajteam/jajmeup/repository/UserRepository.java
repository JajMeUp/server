package com.jajteam.jajmeup.repository;

import com.jajteam.jajmeup.domain.QUser;
import com.jajteam.jajmeup.domain.User;
import com.jajteam.jajmeup.exception.InvalidCredentialException;
import com.jajteam.jajmeup.exception.NoSuchUserException;
import com.jajteam.jajmeup.properties.JajSecurityProperties;
import com.jajteam.jajmeup.security.TokenUtils;
import com.querydsl.jpa.hibernate.HibernateQuery;
import org.hibernate.SessionFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.sql.Date;
import java.time.Instant;

@Repository
public class UserRepository {

    private SessionFactory sessionFactory;
    private PasswordEncoder passwordEncoder;
    private JajSecurityProperties jajSecurityProperties;

    @Inject
    public UserRepository(SessionFactory sessionFactory, PasswordEncoder passwordEncoder, JajSecurityProperties jajSecurityProperties) {
        this.sessionFactory = sessionFactory;
        this.passwordEncoder = passwordEncoder;
        this.jajSecurityProperties = jajSecurityProperties;
    }

    public String encodePassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public void create(User user) {
        user.setCreated(Date.from(Instant.now()));
        if(StringUtils.hasText(user.getPassword())) {
            user.setPassword(encodePassword(user.getPassword()));
        }
        sessionFactory.getCurrentSession().persist(user);
    }

    public User getByUsername(String username) throws NoSuchUserException {
        QUser qUser = QUser.user;
        User user = new HibernateQuery<User>(sessionFactory.getCurrentSession())
                .from(qUser)
                .where(qUser.username.eq(username))
                .fetchFirst();
        if(user != null) {
            return user;
        }
        throw new NoSuchUserException(username);
    }

    public String authenticate(String username, String password) throws InvalidCredentialException {
        User user;
        try {
            user = getByUsername(username);
        } catch (NoSuchUserException e) {
            throw new InvalidCredentialException();
        }
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialException();
        }
        return TokenUtils.generateTokenForUser(user, jajSecurityProperties.getSecretKey());
    }
}
