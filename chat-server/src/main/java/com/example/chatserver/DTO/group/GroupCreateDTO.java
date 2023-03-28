package com.example.chatserver.DTO.group;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GroupCreateDTO {

    private int idAdm;
    private String name;
    private String profilePictureUrl;
    private List<Integer> idMembers;

}
