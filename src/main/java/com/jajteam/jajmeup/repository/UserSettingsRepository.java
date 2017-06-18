package com.jajteam.jajmeup.repository;

import com.jajteam.jajmeup.domain.QUserSettings;
import com.jajteam.jajmeup.domain.Profile;
import com.querydsl.core.types.EntityPath;

public class UserSettingsRepository extends AbstractRepository<Long,Profile> {

    @Override
    public EntityPath<Profile> getEntityPath() {
        return QUserSettings.userSettings;
    }
}
