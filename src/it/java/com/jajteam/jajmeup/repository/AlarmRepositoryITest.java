package com.jajteam.jajmeup.repository;

import com.jajteam.jajmeup.AbstractITest;
import com.jajteam.jajmeup.domain.Alarm;
import com.jajteam.jajmeup.domain.Profile;
import com.jajteam.jajmeup.domain.User;
import org.junit.Test;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;

public class AlarmRepositoryITest extends AbstractITest {

    @Inject
    private UserRepository userRepository;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private AlarmRepository alarmRepository;

    @Test
    public void findLast() {
        Instant now = Instant.now();

        User user = new User();
        user.setUsername("user-test");
        user.setPassword("password-test");
        user.setRole("USER");
        userRepository.create(user);

        Profile profile = new Profile();
        profile.setDisplayName("PROFILE-TEST");
        profile.setVisibility("FRIENDS");
        profile.setPicture("");
        profile.setUser(user);
        profileRepository.persist(profile);

        Alarm alarm = new Alarm();
        alarm.setLink("LINK-1");
        alarm.setMessage("");
        alarm.setVoter(profile);
        alarm.setCreated(Date.from(now));
        alarm.setTarget(profile);

        alarmRepository.persist(alarm);

        Alarm alarm2 = new Alarm();
        alarm2.setLink("LINK-2");
        alarm2.setMessage("");
        alarm2.setVoter(profile);
        alarm2.setCreated(Date.from(now.plus(1, DAYS)));
        alarm2.setTarget(profile);

        alarmRepository.persist(alarm2);

        Alarm alarm3 = new Alarm();
        alarm3.setLink("LINK-3");
        alarm3.setMessage("");
        alarm3.setVoter(profile);
        alarm3.setCreated(Date.from(now.minus(1, DAYS)));
        alarm3.setTarget(profile);

        alarmRepository.persist(alarm3);

        Alarm result = alarmRepository.getLastAlarm(profile.getId());
        assertThat(result.getLink()).isEqualTo("LINK-2");
    }
}
