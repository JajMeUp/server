package com.jajteam.jajmeup.dto.mapper;

import com.jajteam.jajmeup.domain.Alarm;
import com.jajteam.jajmeup.domain.Profile;
import com.jajteam.jajmeup.dto.AlarmDto;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class AlarmDtoMapperTest {

    @Test
    public void mapToDto() throws Exception {
        Date created = Date.from(Instant.now());

        Alarm alarm = new Alarm();
        alarm.setMessage("MESSAGE-TEST");
        alarm.setVoter(new Profile(null, "PROFILE-VOTER"));
        alarm.setTarget(new Profile(null, "PROFILE-TARGET"));
        alarm.setLink("LINK-TEST");
        alarm.setCreated(created);

        AlarmDto expectedResult = new AlarmDto();
        expectedResult.setLink("LINK-TEST");
        expectedResult.setMessage("MESSAGE-TEST");
        expectedResult.setVoterName("PROFILE-VOTER");

        AlarmDto result = AlarmDtoMapper.mapToDto(alarm);
        assertThat(result.getLink()).isEqualTo(expectedResult.getLink());
        assertThat(result.getMessage()).isEqualTo(expectedResult.getMessage());
        assertThat(result.getVoterName()).isEqualTo(expectedResult.getVoterName());
    }
}