package com.jajteam.jajmeup.security;

import com.jajteam.jajmeup.domain.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private User user;
    private String credentials;

    public JwtAuthenticationToken(String rawToken) {
        super(null);
        this.credentials = rawToken;
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(User user, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = user;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.user;
    }
}
