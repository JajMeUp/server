package com.jajteam.jajmeup.command.mapper;

import com.jajteam.jajmeup.command.AlarmCommand;
import com.jajteam.jajmeup.domain.Alarm;
import com.jajteam.jajmeup.domain.Profile;
import com.jajteam.jajmeup.exception.EntityNotFoundException;
import com.jajteam.jajmeup.repository.ProfileRepository;

import javax.inject.Inject;

public class AlarmCommandMapper {

    private ProfileRepository profileRepository;

    @Inject
    public AlarmCommandMapper(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Alarm toAlarm(AlarmCommand command) throws EntityNotFoundException {
        Alarm alarm = new Alarm();
        Profile target = profileRepository.getByKey(command.getTargetId());

        alarm.setTarget(target);
        alarm.setLink(command.getLink());
        alarm.setMessage(command.getMessage());

        return alarm;
    }
}
