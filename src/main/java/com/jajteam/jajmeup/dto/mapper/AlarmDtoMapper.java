package com.jajteam.jajmeup.dto.mapper;

import com.jajteam.jajmeup.domain.Alarm;
import com.jajteam.jajmeup.dto.AlarmDto;

public class AlarmDtoMapper {

    public static AlarmDto mapToDto(Alarm alarm){
        AlarmDto dto = new AlarmDto();
        dto.setMessage(alarm.getMessage());
        dto.setLink(alarm.getLink());
        dto.setVoterName(alarm.getVoter().getUser().getProfile().getDisplayName());

        return dto;
    }

}
