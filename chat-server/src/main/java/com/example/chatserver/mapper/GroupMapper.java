package com.example.chatserver.mapper;

import com.example.chatserver.DTO.group.GroupCreateDTO;
import com.example.chatserver.model.Group;
import com.example.chatserver.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupMapper {

    public Group toGroup(GroupCreateDTO groupCreateDTO, List<User> members) {
        return new Group(
                groupCreateDTO.getIdAdm(),
                groupCreateDTO.getName(),
                groupCreateDTO.getProfilePictureUrl(),
                members
        );
    }

}
