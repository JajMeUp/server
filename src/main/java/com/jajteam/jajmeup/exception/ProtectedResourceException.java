package com.jajteam.jajmeup.exception;

import com.jajteam.jajmeup.domain.Profile;

public class ProtectedResourceException extends Exception {
    public ProtectedResourceException(String className, Long id, Profile profile) {
        super(String.format("User %s [id: %d] does not have access to resource of type %s with id %d",
                profile.getDisplayName(), profile.getId(), className, id));
    }
}
