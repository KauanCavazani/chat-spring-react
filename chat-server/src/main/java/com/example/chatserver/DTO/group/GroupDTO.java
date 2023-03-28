package com.example.chatserver.DTO.group;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GroupDTO {

    private int idGroup;
    private int idAdmin;
    private int idMember;

}
