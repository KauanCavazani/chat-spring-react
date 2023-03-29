package com.example.chatserver.DTO.group;

import com.example.chatserver.DTO.user.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupResponseDTO {

    private int id;
    private int idAdm;
    private String name;
    private String profilePictureUrl;
    private List<UserInfoDTO> members;

}
