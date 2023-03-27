package com.example.chatserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private String profilePictureUrl;
    private boolean isOnline;

    public User(String username, String email, String password, String profilePictureUrl) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePictureUrl = profilePictureUrl;
        this.isOnline = false;
    }
}
