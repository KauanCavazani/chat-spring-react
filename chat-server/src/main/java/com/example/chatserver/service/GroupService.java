package com.example.chatserver.service;

import com.example.chatserver.DTO.group.GroupCreateDTO;
import com.example.chatserver.DTO.group.GroupDTO;
import com.example.chatserver.mapper.GroupMapper;
import com.example.chatserver.model.Group;
import com.example.chatserver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    List<Group> groups;
    private int id;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupMapper mapper;

    public GroupService() {
        this.groups = new ArrayList<>();
        this.id = 1;
    }

    public Group create(GroupCreateDTO newGroupDTO) {
        List<User> members = new ArrayList<>();
        for (User user : userService.getUsers()) {
            System.out.println(newGroupDTO.toString());
            for (int idMember : newGroupDTO.getIdMembers()) {
                if (user.getId() == idMember) {
                    members.add(user);
                }
            }
        }

        Group newGroup = mapper.toGroup(newGroupDTO, members);
        newGroup.setId(id);
        groups.add(newGroup);
        id++;
        return newGroup;
    }

    public boolean addMember(GroupDTO groupAddMemberDTO) {
        for (Group group : groups) {
            if (group.getId() == groupAddMemberDTO.getIdGroup()) {
                if (group.getIdAdm() == groupAddMemberDTO.getIdMember()) {
                    List<User> users = userService.getUsers();
                    for (User user : users) {
                        if (user.getId() == groupAddMemberDTO.getIdMember()) {
                            group.getMembers().add(user);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public List<Group> getByIdMember(int id) {
        List<Group> groupsParticipating = new ArrayList<>();
        for (Group group : groups) {
            for (User member : group.getMembers()) {
                if (member.getId() == id) {
                    groupsParticipating.add(group);
                }
            }
        }

        return groupsParticipating;
    }

    public boolean deleteMember(GroupDTO groupDTO) {
        for (Group group : groups) {
            if (group.getId() == groupDTO.getIdGroup()) {
                if (group.getIdAdm() == groupDTO.getIdAdmin()) {
                    for (User member : group.getMembers()) {
                        if (member.getId() == groupDTO.getIdMember()) {
                            group.getMembers().remove(member);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean leave(GroupDTO groupDTO) {
        for (Group group : groups) {
            if (group.getId() == groupDTO.getIdGroup()) {
                for (User member : group.getMembers()) {
                    if (member.getId() == groupDTO.getIdMember()) {
                        group.getMembers().remove(member);
                        return true;
                    }
                }
            }
        }

        return false;
    }

}
