package com.jajteam.jajmeup.dto.mapper;

import com.jajteam.jajmeup.domain.Friendship;
import com.jajteam.jajmeup.dto.FriendshipDto;

import java.util.List;
import java.util.stream.Collectors;

public class FriendshipDtoMapper {

    public static FriendshipDto mapToDto(Friendship friendship) {
        FriendshipDto dto = new FriendshipDto();
        dto.setId(friendship.getId());
        dto.setRequesterName(friendship.getRequester().getDisplayName());

        return dto;
    }

    public static List<FriendshipDto> toFriendshipDtoList(List<Friendship> friendships) {
        return friendships.stream().map(FriendshipDtoMapper::mapToDto).collect(Collectors.toList());
    }
}
