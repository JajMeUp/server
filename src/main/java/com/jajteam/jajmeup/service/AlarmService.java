package com.jajteam.jajmeup.service;

import com.jajteam.jajmeup.command.AlarmCommand;
import com.jajteam.jajmeup.command.mapper.AlarmCommandMapper;
import com.jajteam.jajmeup.domain.Alarm;
import com.jajteam.jajmeup.exception.EntityNotFoundException;
import com.jajteam.jajmeup.exception.InvalidEntityException;
import com.jajteam.jajmeup.repository.AlarmRepository;
import com.jajteam.jajmeup.validation.AlarmValidator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

public class AlarmService extends AbstractService {

    private AlarmRepository alarmRepository;
    private AlarmCommandMapper mapper;
    private AlarmValidator validator;

    public AlarmService(AlarmRepository repository, AlarmCommandMapper mapper, AlarmValidator validator) {
        this.alarmRepository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Transactional
    public Alarm create(AlarmCommand command) throws EntityNotFoundException, InvalidEntityException {
        Alarm alarm = mapper.toAlarm(command);
        alarm.setVoter(getAuthenticatedUser().getProfile());

        BindingResult result = new BeanPropertyBindingResult(alarm, "alarm");
        validator.validate(alarm, result);
        if(!result.hasErrors()) {
            alarm.setCreated(Date.from(Instant.now()));
            alarmRepository.persist(alarm);
            return alarm;
        }
        throw new InvalidEntityException(Alarm.class.getSimpleName(), result.getAllErrors());
    }

    @Transactional
    public Alarm getLastAlarm(){
        return alarmRepository.getLastAlarm(getAuthenticatedUser().getProfile().getId());
    }
}
