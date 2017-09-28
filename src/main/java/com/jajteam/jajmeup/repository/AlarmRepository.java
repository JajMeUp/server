package com.jajteam.jajmeup.repository;

import com.jajteam.jajmeup.domain.QAlarm;
import com.jajteam.jajmeup.domain.Alarm;
import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.hibernate.HibernateQuery;

import java.util.List;

public class AlarmRepository extends AbstractRepository<Long, Alarm> {

    @Override
    public EntityPath<Alarm> getEntityPath() {
        return QAlarm.alarm;
    }

    public List<Alarm> getLastAlarm(Long requesterId) { //toujours des listes ?
        QAlarm qAlarm = QAlarm.alarm;
        return new HibernateQuery<Alarm>(getSession())
                .from(qAlarm)
                .where(qAlarm.target.id.eq(requesterId))
                .fetch();
    }
}
