package com.jajteam.jajmeup.service;

import com.jajteam.jajmeup.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractService {

    protected User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
