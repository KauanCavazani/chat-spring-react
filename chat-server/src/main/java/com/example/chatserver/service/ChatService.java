package com.example.chatserver.service;

import com.example.chatserver.DTO.chat.ChatDTO;
import com.example.chatserver.mapper.UserMapper;
import com.example.chatserver.model.Group;
import com.example.chatserver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    public ChatDTO getChats(int id) {
        ChatDTO chatDTO = new ChatDTO();
        List<User> users = userService.getUsers();
        List<Group> groups = groupService.getByIdMember(id);

        for (User user : users) {
            chatDTO.getUsers().add(userMapper.toUserInfoDTO(user));
        }

        for (Group group : groups) {
            chatDTO.getGroups().add(group);
        }

        return chatDTO;
    }

}
