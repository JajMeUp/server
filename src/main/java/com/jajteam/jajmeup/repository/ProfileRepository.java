package com.jajteam.jajmeup.repository;

import com.jajteam.jajmeup.domain.Profile;
import com.jajteam.jajmeup.domain.QProfile;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.hibernate.HibernateQuery;

import java.util.List;

public class ProfileRepository extends AbstractRepository<Long,Profile> {

    @Override
    public EntityPath<Profile> getEntityPath() {
        return QProfile.profile;
    }

    public List<Profile> findProfileByNameSearch(String search) {
        QProfile qProfile = QProfile.profile;

        return new HibernateQuery<Profile>(getSession()).from(qProfile)
                .where(qProfile.displayName.likeIgnoreCase("%" + search + "%"))
                .fetch();
    }
}
