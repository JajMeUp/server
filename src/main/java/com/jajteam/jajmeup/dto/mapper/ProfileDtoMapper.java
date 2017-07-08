package com.jajteam.jajmeup.dto.mapper;

import com.jajteam.jajmeup.domain.Profile;
import com.jajteam.jajmeup.dto.ProfileDto;

import java.util.List;
import java.util.stream.Collectors;

public class ProfileDtoMapper {

    public static ProfileDto toProfileDto(Profile profile) {
        ProfileDto dto = new ProfileDto();
        dto.setId(profile.getId());
        dto.setDisplayName(profile.getDisplayName());
        dto.setPicture(profile.getPicture());

        return dto;
    }

    public static List<ProfileDto> toProfileDtoList(List<Profile> profiles) {
        return profiles.stream().map(ProfileDtoMapper::toProfileDto).collect(Collectors.toList());
    }
}
