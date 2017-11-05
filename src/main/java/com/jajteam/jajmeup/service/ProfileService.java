package com.jajteam.jajmeup.service;

import com.jajteam.jajmeup.command.ProfileCommand;
import com.jajteam.jajmeup.domain.Profile;
import com.jajteam.jajmeup.dto.ProfileDto;
import com.jajteam.jajmeup.dto.mapper.ProfileDtoMapper;
import com.jajteam.jajmeup.exception.EntityNotFoundException;
import com.jajteam.jajmeup.exception.InvalidEntityException;
import com.jajteam.jajmeup.repository.ProfileRepository;
import com.jajteam.jajmeup.validation.ProfileValidator;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.List;

public class ProfileService extends AbstractService {

    private ProfileRepository profileRepository;
    private ProfileValidator profileValidator;

    public ProfileService(ProfileRepository profileRepository, ProfileValidator profileValidator) {
        this.profileRepository = profileRepository;
        this.profileValidator = profileValidator;
    }

    @Transactional(readOnly = true)
    public List<ProfileDto> findProfileByNameSearch(String search) {
        return ProfileDtoMapper.toProfileDtoList(profileRepository.findProfileByNameSearch(search, getAuthenticatedUser().getProfile().getId()));
    }

    @Transactional
    public void update(ProfileCommand command) throws InvalidEntityException, EntityNotFoundException {
        BindingResult result = new BeanPropertyBindingResult(command, "ProfileCommand");
        profileValidator.validate(command, result);

        if(result.hasErrors()) {
            throw new InvalidEntityException("ProfileCommand", result.getAllErrors());
        }

        Profile profile = profileRepository.getByKey(getAuthenticatedUser().getProfile().getId());
        profile.setVisibility(command.getVisibility());
    }

    @Transactional(readOnly = true)
    public List<ProfileDto> findProfileByNameAndActiveFriendship(String search) {
        return ProfileDtoMapper.toProfileDtoList(profileRepository.findProfileByNameAndActiveFriendship(search, getAuthenticatedUser().getProfile().getId()));
    }
}
