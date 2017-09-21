package com.jajteam.jajmeup.validation;

import com.jajteam.jajmeup.command.ProfileCommand;
import com.jajteam.jajmeup.domain.Profile;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProfileValidator implements Validator {

    private static final String[] authorizedVisibilityValues = {Profile.VISIBILITY_PRIVATE, Profile.VISIBILITY_FRIENDS, Profile.VISIBILITY_WORLD};

    @Override
    public boolean supports(Class<?> aClass) {
        return ProfileCommand.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProfileCommand command = (ProfileCommand) o;
        boolean validVisibility = false;

        for(String s : authorizedVisibilityValues) {
            if(command.getVisibility().equals(s)) {
                validVisibility = true;
            }
        }
        if(!validVisibility) {
            errors.rejectValue("visibility", "valid", "The visibility value is NOT valid");
        }
    }
}
