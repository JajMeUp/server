package com.jajteam.jajmeup.validation;

import com.jajteam.jajmeup.domain.Alarm;
import com.jajteam.jajmeup.exception.EntityNotFoundException;
import com.jajteam.jajmeup.repository.ProfileRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.inject.Inject;

public class AlarmValidator implements Validator {

    private ProfileRepository profileRepository;

    @Inject
    public AlarmValidator(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Alarm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Alarm alarm = (Alarm) o;
        try {
            profileRepository.getByKey(alarm.getVoter().getId());
        } catch (EntityNotFoundException e) {
            errors.rejectValue("voter", "exists", "The voter has to be an existing user");
        }
        try {
            profileRepository.getByKey(alarm.getTarget().getId());
        } catch (EntityNotFoundException e) {
            errors.rejectValue("target", "exists", "The target has to be an existing user");
        }

        // TODO checks that the voter can vote for the given target
    }
}
