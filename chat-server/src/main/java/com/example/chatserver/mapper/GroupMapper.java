package com.example.chatserver.mapper;

import com.example.chatserver.DTO.group.GroupCreateDTO;
import com.example.chatserver.DTO.group.GroupResponseDTO;
import com.example.chatserver.DTO.user.UserInfoDTO;
import com.example.chatserver.model.Group;
import com.example.chatserver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupMapper {

    @Autowired
    private UserMapper userMapper;

    public Group toGroup(GroupCreateDTO groupCreateDTO, List<User> members) {
        return new Group(
                groupCreateDTO.getIdAdmin(),
                groupCreateDTO.getName(),
                groupCreateDTO.getProfilePictureUrl(),
                members
        );
    }

    public GroupResponseDTO toGroupResponseDTO(Group group, List<User> members) {
        return new GroupResponseDTO(
                group.getId(),
                group.getIdAdm(),
                group.getName(),
                group.getProfilePictureUrl(),
                userMapper.toUserInfoListDTO(members)
        );
    }

    public List<GroupResponseDTO> toGroupResponseListDTO(List<Group> groups) {
        List<GroupResponseDTO> groupsResponseDTO = new ArrayList<>();
        for (Group group : groups) {
            groupsResponseDTO.add(toGroupResponseDTO(group, group.getMembers()));
        }

        return groupsResponseDTO;
    }

}
