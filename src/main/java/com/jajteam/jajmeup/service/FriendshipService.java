package com.jajteam.jajmeup.service;

import com.jajteam.jajmeup.domain.Friendship;
import com.jajteam.jajmeup.domain.User;
import com.jajteam.jajmeup.dto.FriendshipDto;
import com.jajteam.jajmeup.dto.mapper.FriendshipDtoMapper;
import com.jajteam.jajmeup.exception.EntityNotFoundException;
import com.jajteam.jajmeup.exception.FriendshipAlreadyAnsweredException;
import com.jajteam.jajmeup.exception.InvalidEntityException;
import com.jajteam.jajmeup.exception.ProtectedResourceException;
import com.jajteam.jajmeup.repository.FriendshipRepository;
import com.jajteam.jajmeup.repository.ProfileRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class FriendshipService extends AbstractService {

    private FriendshipRepository friendshipRepository;
    private ProfileRepository profileRepository;

    public FriendshipService(FriendshipRepository friendshipRepository, ProfileRepository profileRepository) {
        this.friendshipRepository = friendshipRepository;
        this.profileRepository = profileRepository;
    }

    @Transactional
    public Friendship create(Long targetId) throws InvalidEntityException {
        Friendship friendship = new Friendship();
        BindingResult result = new BeanPropertyBindingResult(friendship, "friendship");

        // Checks that both requester and target are valid profiles
        try {
            friendship.setRequester(profileRepository.getByKey(getAuthenticatedUser().getProfile().getId()));
        } catch (EntityNotFoundException e) {
            result.rejectValue("requester", "exists", "The requester has to be an existing profile");
        }
        try {
            friendship.setTarget(profileRepository.getByKey(targetId));
        } catch (EntityNotFoundException e) {
            result.rejectValue("target", "exists", "The target has to be an existing profile");
        }

        // Checks that requester and target are not the same profile
        if(targetId.equals(friendship.getRequester().getId())) {
            result.rejectValue("target", "valid", "The target can NOT be equals to the requester");
        }

        // Checks that a friendship does not already exist between requester and target
        if(friendshipRepository.existsFriendshipBetween(getAuthenticatedUser().getProfile().getId(), targetId)) {
            result.rejectValue("target", "valid", "This friendship already exists");
        }

        if(result.hasErrors()) {
            throw new InvalidEntityException(Friendship.class.getSimpleName(), result.getAllErrors());
        }

        friendship.setStatus(Friendship.PENDING);
        friendship.setUpdated(Date.from(Instant.now()));

        friendshipRepository.persist(friendship);
        return friendship;
    }

    @Transactional
    public List<FriendshipDto> getPendingRequests() {
        return FriendshipDtoMapper.toFriendshipDtoList(friendshipRepository.findPendingRequests(getAuthenticatedUser().getProfile().getId()));
    }

    @Transactional
    public void answerFriendRequest(Long id, String answer) throws EntityNotFoundException, ProtectedResourceException, FriendshipAlreadyAnsweredException {
        Friendship friendship = friendshipRepository.getByKey(id);
        User user = getAuthenticatedUser();

        if(!friendship.getTarget().getId().equals(user.getProfile().getId())) {
            throw new ProtectedResourceException(Friendship.class.getSimpleName(), id, user.getProfile());
        }
        if(!friendship.getStatus().equals(Friendship.PENDING)) {
            throw new FriendshipAlreadyAnsweredException(id);
        }

        friendship.setStatus(answer);
        friendship.setUpdated(Date.from(Instant.now()));
    }
}
