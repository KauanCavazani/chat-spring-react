package com.example.chatserver.service;

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

    public User register(User newUser) {
        newUser.setId(id);
        id++;
        users.add(newUser);
        return newUser;
    }

    public User login(User userLogin) {
        for (User user : users) {
            if (user.getEmail().equals(userLogin.getEmail()) &&
                user.getPassword().equals(userLogin.getPassword())
            ) {
                user.setOnline(true);
                return user;
            }
        }

        return null;
    }

    public boolean logoff(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                user.setOnline(false);
                return true;
            }
        }

        return false;
    }

    public List<User> getUsers() {
        return users;
    }

}
