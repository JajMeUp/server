package com.jajteam.jajmeup.repository;

import com.jajteam.jajmeup.AbstractITest;
import com.jajteam.jajmeup.domain.Profile;
import com.jajteam.jajmeup.domain.User;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ProfileRepositoryITests extends AbstractITest {

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private UserRepository userRepository;

    private List<Profile> profiles;

    @Before
    public void setUp() {
        profiles = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            User user = new User();
            user.setUsername("user-test-" + i);
            user.setPassword("password-test-" + i);
            user.setRole("USER");
            userRepository.create(user);

            Profile profile = new Profile(user, "display-name-" + i);
            profileRepository.persist(profile);

            profiles.add(profile);
        }
    }

    @Test
    public void findProfileByNameSearch() {
        List<Profile> result = profileRepository.findProfileByNameSearch("display");
        assertThat(result).isEqualTo(profiles);

        result = profileRepository.findProfileByNameSearch("DisPLay");
        assertThat(result).isEqualTo(profiles);

        result = profileRepository.findProfileByNameSearch("ay-name");
        assertThat(result).isEqualTo(profiles);

        result = profileRepository.findProfileByNameSearch("no-such-name-bro");
        assertThat(result).hasSize(0);

        result = profileRepository.findProfileByNameSearch("1");
        assertThat(result).isEqualTo(profiles.stream().filter(p -> p.getDisplayName().contains("1")).collect(Collectors.toList()));
    }
}
