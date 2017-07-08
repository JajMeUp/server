package com.jajteam.jajmeup.configuration;

import com.jajteam.jajmeup.command.mapper.AlarmCommandMapper;
import com.jajteam.jajmeup.properties.JajSecurityProperties;
import com.jajteam.jajmeup.repository.AlarmRepository;
import com.jajteam.jajmeup.repository.FriendshipRepository;
import com.jajteam.jajmeup.repository.ProfileRepository;
import com.jajteam.jajmeup.repository.UserRepository;
import com.jajteam.jajmeup.security.JwtAuthenticationProvider;
import com.jajteam.jajmeup.service.AlarmService;
import com.jajteam.jajmeup.service.FriendshipService;
import com.jajteam.jajmeup.service.ProfileService;
import com.jajteam.jajmeup.service.UserService;
import com.jajteam.jajmeup.validation.AlarmValidator;
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
    public ProfileRepository userSettingsRepository() {
        return new ProfileRepository();
    }

    @Bean
    public AlarmRepository alarmRepository() {
        return new AlarmRepository();
    }

    @Bean
    public FriendshipRepository friendshipRepository() {
        return new FriendshipRepository();
    }

    @Bean
    public ProfileRepository profileRepository() {
        return new ProfileRepository();
    }

    /* Services */
    @Bean
    public UserService userService(UserRepository userRepository, UserValidator userValidator, ProfileRepository profileRepository) {
        return new UserService(userRepository, userValidator, profileRepository);
    }

    @Bean
    public AlarmService alarmService(AlarmRepository repository, AlarmCommandMapper mapper, AlarmValidator validator) {
        return new AlarmService(repository, mapper, validator);
    }

    @Bean
    public FriendshipService friendshipService(FriendshipRepository repository, ProfileRepository profileRepository) {
        return new FriendshipService(repository, profileRepository);
    }

    @Bean
    public ProfileService profileService(ProfileRepository profileRepository) {
        return new ProfileService(profileRepository);
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

    @Bean
    public AlarmValidator alarmValidator(ProfileRepository profileRepository) {
        return new AlarmValidator(profileRepository);
    }

    /* Mappers */
    @Bean
    public AlarmCommandMapper alarmCommandMapper(ProfileRepository profileRepository) {
        return new AlarmCommandMapper(profileRepository);
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
