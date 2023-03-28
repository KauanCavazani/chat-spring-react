package com.example.chatserver.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRegisterLoginDTO {

    private String username;
    private String email;
    private String password;
    private String profilePictureUrl;

}
