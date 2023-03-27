package com.example.chatserver.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoDTO {

    private int id;
    private String username;
    private String profilePictureUrl;
    private boolean isOnline;

}
