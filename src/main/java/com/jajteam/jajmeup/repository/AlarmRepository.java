package com.jajteam.jajmeup.repository;

import com.jajteam.jajmeup.domain.QAlarm;
import com.jajteam.jajmeup.domain.Alarm;
import com.querydsl.core.types.EntityPath;

public class AlarmRepository extends AbstractRepository<Long, Alarm> {

    @Override
    public EntityPath<Alarm> getEntityPath() {
        return QAlarm.alarm;
    }
}
