package com.jajteam.jajmeup.repository;

import com.jajteam.jajmeup.domain.Profile;
import com.jajteam.jajmeup.domain.QProfile;
import com.querydsl.core.types.EntityPath;

public class ProfileRepository extends AbstractRepository<Long,Profile> {

    @Override
    public EntityPath<Profile> getEntityPath() {
        return QProfile.profile;
    }
}
