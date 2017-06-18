package com.jajteam.jajmeup.configuration;

import com.jajteam.jajmeup.properties.JajSecurityProperties;
import com.jajteam.jajmeup.repository.UserRepository;
import com.jajteam.jajmeup.repository.UserSettingsRepository;
import com.jajteam.jajmeup.security.JwtAuthenticationProvider;
import com.jajteam.jajmeup.service.UserService;
import com.jajteam.jajmeup.validation.UserValidator;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class BeansConfiguration {

    /* Repositories */
    @Bean
    public UserRepository userRepository(SessionFactory sessionFactory, PasswordEncoder passwordEncoder, JajSecurityProperties jajSecurityProperties) {
        return new UserRepository(sessionFactory, passwordEncoder, jajSecurityProperties);
    }

    @Bean
    public UserSettingsRepository userSettingsRepository() {
        return new UserSettingsRepository();
    }

    /* Services */
    @Bean
    public UserService userService(UserRepository userRepository, UserValidator userValidator, UserSettingsRepository userSettingsRepository) {
        return new UserService(userRepository, userValidator, userSettingsRepository);
    }

    /* Validators */
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public UserValidator userValidator(UserRepository userRepository) {
        return new UserValidator(userRepository);
    }

    /* Others */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JajSecurityProperties jajSecurityProperties() {
        return new JajSecurityProperties();
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider(UserService userService, JajSecurityProperties jajSecurityProperties) {
        return new JwtAuthenticationProvider(userService, jajSecurityProperties);
    }
}
