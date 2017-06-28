package com.jajteam.jajmeup.repository;

import com.jajteam.jajmeup.domain.Friendship;
import com.jajteam.jajmeup.domain.QFriendship;
import com.querydsl.core.types.EntityPath;

public class FriendshipRepository extends AbstractRepository<Long, Friendship> {

    @Override
    public EntityPath<Friendship> getEntityPath() {
        return QFriendship.friendship;
    }
}
