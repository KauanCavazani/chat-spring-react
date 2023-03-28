package com.example.chatserver.DTO.chat;

import com.example.chatserver.DTO.user.UserInfoDTO;
import com.example.chatserver.model.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatDTO {

    private List<Group> groups;
    private List<UserInfoDTO> users;

}
