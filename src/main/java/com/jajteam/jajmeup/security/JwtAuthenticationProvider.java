package com.jajteam.jajmeup.security;

import com.jajteam.jajmeup.domain.User;
import com.jajteam.jajmeup.exception.NoSuchUserException;
import com.jajteam.jajmeup.properties.JajSecurityProperties;
import com.jajteam.jajmeup.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;
    private JajSecurityProperties jajSecurityProperties;

    @Inject
    public JwtAuthenticationProvider(UserService userService, JajSecurityProperties jajSecurityProperties) {
        this.userService = userService;
        this.jajSecurityProperties = jajSecurityProperties;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;

        final String token = (String) authenticationToken.getCredentials();
        if(StringUtils.isEmpty(token)) {
            throw new AuthenticationCredentialsNotFoundException("The security token is empty");
        }
        final Claims jwsClaims = TokenUtils.parseClaims(token, jajSecurityProperties.getSecretKey());
        final String subject = jwsClaims.getSubject();
        final String role = jwsClaims.get(TokenUtils.USER_ROLE, String.class);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(!StringUtils.isEmpty(role)) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        User user;
        try {
            user = userService.getByUsername(subject);
        } catch (NoSuchUserException e) {
            throw new UsernameNotFoundException(String.format("User %s does NOT exist", subject));
        }
        return new JwtAuthenticationToken(user, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
