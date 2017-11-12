package com.jajteam.jajmeup.repository;

import com.jajteam.jajmeup.domain.QAlarm;
import com.jajteam.jajmeup.domain.Alarm;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.hibernate.HibernateQuery;

public class AlarmRepository extends AbstractRepository<Long, Alarm> {

    @Override
    public EntityPath<Alarm> getEntityPath() {
        return QAlarm.alarm;
    }

    public Alarm getLastAlarm(Long requesterId) {
        QAlarm qAlarm = QAlarm.alarm;
        //TODO
        //check if result
        return new HibernateQuery<Alarm>(getSession())
                .from(qAlarm)
                .where(qAlarm.target.id.eq(requesterId))
                .orderBy(qAlarm.created.desc())
                .limit(1)
                .fetchOne();
    }
}
