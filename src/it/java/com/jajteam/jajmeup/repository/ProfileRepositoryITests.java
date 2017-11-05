package com.jajteam.jajmeup.repository;

import com.jajteam.jajmeup.AbstractITest;
import com.jajteam.jajmeup.domain.Friendship;
import com.jajteam.jajmeup.domain.Profile;
import com.jajteam.jajmeup.domain.User;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ProfileRepositoryITests extends AbstractITest {

    public static final int USER_NUMBER = 10;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private FriendshipRepository friendshipRepository;

    @Inject
    private UserRepository userRepository;

    private List<Profile> profiles;

    @Before
    public void setUp() {
        profiles = new ArrayList<>();

        for(int i = 0; i < USER_NUMBER; i++) {
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
    public void findProfileByNameSearch_should_filter_by_name_with_any_case() {
        Profile searcher = profiles.get(0);
        List<Profile> expectedResult = new ArrayList<>(profiles);
        expectedResult.remove(searcher);

        List<Profile> result = profileRepository.findProfileByNameSearch("display", searcher.getId());
        assertThat(result).extracting("id").isEqualTo(expectedResult.stream().map(Profile::getId).collect(Collectors.toList()));

        result = profileRepository.findProfileByNameSearch("DisPLay", searcher.getId());
        assertThat(result).extracting("id").isEqualTo(expectedResult.stream().map(Profile::getId).collect(Collectors.toList()));

        result = profileRepository.findProfileByNameSearch("ay-name", searcher.getId());
        assertThat(result).extracting("id").isEqualTo(expectedResult.stream().map(Profile::getId).collect(Collectors.toList()));

        result = profileRepository.findProfileByNameSearch("no-such-name-bro", searcher.getId());
        assertThat(result).extracting("id").hasSize(0);

        result = profileRepository.findProfileByNameSearch("1", searcher.getId());
        assertThat(result).extracting("id").isEqualTo(expectedResult.stream().filter(p -> p.getDisplayName().contains("1")).map(Profile::getId).collect(Collectors.toList()));
    }

    @Test
    public void findProfileByNameSearch_should_filter_by_non_existing_friendship() {
        Date creationDate = Date.from(Instant.now());

        Friendship friendship = new Friendship();
        friendship.setStatus(Friendship.PENDING);
        friendship.setRequester(profiles.get(0));
        friendship.setTarget(profiles.get(1));
        friendship.setUpdated(creationDate);

        friendshipRepository.persist(friendship);

        Friendship friendship2 = new Friendship();
        friendship2.setStatus(Friendship.PENDING);
        friendship2.setRequester(profiles.get(2));
        friendship2.setTarget(profiles.get(0));
        friendship2.setUpdated(creationDate);

        friendshipRepository.persist(friendship2);

        List<Profile> expectedResults = new ArrayList<>(profiles);
        expectedResults.remove(profiles.get(0));
        expectedResults.remove(profiles.get(1));
        expectedResults.remove(profiles.get(2));

        List<Profile> result = profileRepository.findProfileByNameSearch("", profiles.get(0).getId());
        assertThat(result).extracting("id").isEqualTo(expectedResults.stream().map(Profile::getId).collect(Collectors.toList()));
    }

    @Test
    public void findProfileByNameAndActiveFriendship() {
        Date creationDate = Date.from(Instant.now());

        Friendship friendship = new Friendship();
        friendship.setStatus(Friendship.ACCEPTED);
        friendship.setRequester(profiles.get(0));
        friendship.setTarget(profiles.get(1));
        friendship.setUpdated(creationDate);

        friendshipRepository.persist(friendship);

        Friendship friendship2 = new Friendship();
        friendship2.setStatus(Friendship.ACCEPTED);
        friendship2.setRequester(profiles.get(0));
        friendship2.setTarget(profiles.get(2));
        friendship2.setUpdated(creationDate);

        friendshipRepository.persist(friendship2);

        List<Profile> result = profileRepository.findProfileByNameAndActiveFriendship("", profiles.get(0).getId());
        assertThat(result).extracting("id").isEqualTo(Stream.of(profiles.get(1), profiles.get(2)).map(Profile::getId).collect(Collectors.toList()));

        result = profileRepository.findProfileByNameAndActiveFriendship("2", profiles.get(0).getId());
        assertThat(result).extracting("id").isEqualTo(Stream.of(profiles.get(2)).map(Profile::getId).collect(Collectors.toList()));
    }
}
