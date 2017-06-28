package com.jajteam.jajmeup.service;

import com.jajteam.jajmeup.domain.Friendship;
import com.jajteam.jajmeup.exception.EntityNotFoundException;
import com.jajteam.jajmeup.exception.InvalidEntityException;
import com.jajteam.jajmeup.repository.FriendshipRepository;
import com.jajteam.jajmeup.repository.ProfileRepository;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Date;

public class FriendshipService extends AbstractService {

    private FriendshipRepository friendshipRepository;
    private ProfileRepository profileRepository;

    @Inject
    public FriendshipService(FriendshipRepository friendshipRepository, ProfileRepository profileRepository) {
        this.friendshipRepository = friendshipRepository;
        this.profileRepository = profileRepository;
    }

    public Friendship create(Long requesterId, Long targetId) throws InvalidEntityException {
        Friendship friendship = new Friendship();
        BindingResult result = new BeanPropertyBindingResult(friendship, "friendship");

        try {
            friendship.setRequester(profileRepository.getByKey(requesterId));
        } catch (EntityNotFoundException e) {
            result.rejectValue("requester", "exists", "The requester has to be an existing profile");
        }
        try {
            friendship.setTarget(profileRepository.getByKey(targetId));
        } catch (EntityNotFoundException e) {
            result.rejectValue("target", "exists", "The target has to be an existing profile");
        }

        if(result.hasErrors()) {
            throw new InvalidEntityException(Friendship.class.getSimpleName(), result.getAllErrors());
        }

        friendship.setStatus(Friendship.PENDING);
        friendship.setUpdated(Date.from(Instant.now()));

        friendshipRepository.persist(friendship);
        return friendship;
    }
}
