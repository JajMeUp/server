package com.jajteam.jajmeup.service;

import com.jajteam.jajmeup.command.AlarmCommand;
import com.jajteam.jajmeup.command.mapper.AlarmCommandMapper;
import com.jajteam.jajmeup.domain.Alarm;
import com.jajteam.jajmeup.exception.EntityNotFoundException;
import com.jajteam.jajmeup.exception.InvalidAlarmException;
import com.jajteam.jajmeup.repository.AlarmRepository;
import com.jajteam.jajmeup.validation.AlarmValidator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import javax.inject.Inject;
import java.sql.Date;
import java.time.Instant;

public class AlarmService extends AbstractService {

    private AlarmRepository repository;
    private AlarmCommandMapper mapper;
    private AlarmValidator validator;

    @Inject
    public AlarmService(AlarmRepository repository, AlarmCommandMapper mapper, AlarmValidator validator) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Transactional
    public Alarm create(AlarmCommand command) throws EntityNotFoundException, InvalidAlarmException {
        Alarm alarm = mapper.toAlarm(command);
        alarm.setVoter(getAuthenticatedUser().getProfile());

        BindingResult result = new BeanPropertyBindingResult(alarm, "alarm");
        validator.validate(alarm, result);
        if(!result.hasErrors()) {
            alarm.setCreated(Date.from(Instant.now()));
            repository.persist(alarm);
            return alarm;
        }
        throw new InvalidAlarmException(result);
    }
}
