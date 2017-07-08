package com.jajteam.jajmeup.service;

import com.jajteam.jajmeup.dto.ProfileDto;
import com.jajteam.jajmeup.dto.mapper.ProfileDtoMapper;
import com.jajteam.jajmeup.repository.ProfileRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

public class ProfileService extends AbstractService {

    private ProfileRepository profileRepository;

    @Inject
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Transactional
    public List<ProfileDto> findProfileByNameSearch(String search) {
        return ProfileDtoMapper.toProfileDtoList(profileRepository.findProfileByNameSearch(search));
    }
}
