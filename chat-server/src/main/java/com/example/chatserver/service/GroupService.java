package com.example.chatserver.service;

import com.example.chatserver.DTO.group.GroupCreateDTO;
import com.example.chatserver.DTO.group.GroupResponseDTO;
import com.example.chatserver.mapper.GroupMapper;
import com.example.chatserver.model.Group;
import com.example.chatserver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public GroupResponseDTO create(GroupCreateDTO newGroupDTO) {
        List<User> members = new ArrayList<>();
        for (User user : userService.getUsers()) {
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
        return mapper.toGroupResponseDTO(newGroup, members);
    }

    public boolean addMember(int idGroup, int idAdmin, int idMember) {
        for (Group group : groups) {
            if (group.getId() == idGroup) {
                if (group.getIdAdm() == idAdmin) {
                    for (User member : group.getMembers()) {
                        if (member.getId() == idMember) {
                            return false;
                        }
                    }

                    List<User> users = userService.getUsers();
                    for (User user : users) {
                        if (user.getId() == idMember) {
                            group.getMembers().add(user);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public List<GroupResponseDTO> getByIdMember(int id) {
        List<Group> groupsParticipating = new ArrayList<>();
        for (Group group : groups) {
            for (User member : group.getMembers()) {
                if (member.getId() == id) {
                    groupsParticipating.add(group);
                }
            }
        }

        return mapper.toGroupResponseListDTO(groupsParticipating);
    }

    public boolean deleteMember(int idGroup, int idAdmin, int idMember) {
        for (Group group : groups) {
            if (group.getId() == idGroup) {
                if (group.getIdAdm() == idAdmin) {
                    for (User member : group.getMembers()) {
                        if (member.getId() != idAdmin && member.getId() == idMember) {
                            group.getMembers().remove(member);
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean leave(int idGroup, int idMember) {
        for (Group group : groups) {
            if (group.getId() == idGroup) {
                for (User member : group.getMembers()) {
                    if (member.getId() == idMember) {
                        group.getMembers().remove(member);
                        group.setIdAdm(getRandomMemberId(group));
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private int getRandomMemberId(Group group) {
        Random random = new Random();
        int randomIndex = random.nextInt(group.getMembers().size());
        return group.getMembers().get(randomIndex).getId();
    }

}
