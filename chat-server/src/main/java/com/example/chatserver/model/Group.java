package com.example.chatserver.model;

import com.example.chatserver.DTO.user.UserInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Group {

    private int id;
    private int idAdm;
    private String name;
    private String profilePictureUrl;
    private List<User> members;

    public Group(int idAdm, String name, String profilePictureUrl, List<User> members) {
        this.idAdm = idAdm;
        this.name = name;
        this.profilePictureUrl = profilePictureUrl;
        this.members = members;
    }
}
