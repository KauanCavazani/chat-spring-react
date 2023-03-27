package com.example.chatserver.service;

import com.example.chatserver.DTO.UserInfoDTO;
import com.example.chatserver.DTO.UserRegisterLoginDTO;
import com.example.chatserver.mapper.UserMapper;
import com.example.chatserver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper mapper;
    private List<User> users;
    private int id;

    public UserService() {
        this.users = new ArrayList<>();
        this.id = 1;
    }

    public UserInfoDTO register(UserRegisterLoginDTO newUserDTO) {
        User newUser = mapper.toUser(newUserDTO);
        for (User user : users) {
            if (!user.getEmail().equals(newUser.getEmail()) &&
                !user.getPassword().equals(newUser.getPassword()) &&
                !user.getUsername().equals(newUser.getUsername())
            ) {
                newUser.setId(id);
                id++;
                users.add(newUser);
                return mapper.toUserInfoDTO(newUser);
            }
        }

        return null;
    }

    public UserInfoDTO login(UserRegisterLoginDTO userLogin) {
        for (User user : users) {
            if (user.getEmail().equals(userLogin.getEmail()) &&
                user.getPassword().equals(userLogin.getPassword())
            ) {
                user.setOnline(true);
                return mapper.toUserInfoDTO(user);
            }
        }

        return null;
    }

}
