package com.jajteam.jajmeup.repository;

import com.jajteam.jajmeup.domain.Friendship;
import com.jajteam.jajmeup.domain.QFriendship;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.hibernate.HibernateQuery;

import java.util.List;

public class FriendshipRepository extends AbstractRepository<Long, Friendship> {

    @Override
    public EntityPath<Friendship> getEntityPath() {
        return QFriendship.friendship;
    }

    public List<Friendship> findPendingRequests(Long targetId) {
        QFriendship qFriendship = QFriendship.friendship;

        return new HibernateQuery<Friendship>(getSession())
                .from(qFriendship)
                .where(qFriendship.target.id.eq(targetId))
                .where(qFriendship.status.eq(Friendship.PENDING))
                .fetch();
    }

    public boolean existsFriendshipBetween(Long requesterId, Long targetId) {
        QFriendship qFriendship = QFriendship.friendship;

        long tmp = new HibernateQuery<Friendship>(getSession())
                .from(qFriendship)
                .where(qFriendship.requester.id.eq(requesterId).and(qFriendship.target.id.eq(targetId)))
                .fetchCount();
        return tmp + new HibernateQuery<Friendship>(getSession())
                .from(qFriendship)
                .where(qFriendship.target.id.eq(requesterId).and(qFriendship.requester.id.eq(targetId)))
                .fetchCount() > 0;
    }
}
