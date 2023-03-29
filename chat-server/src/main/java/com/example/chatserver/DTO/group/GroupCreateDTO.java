package com.example.chatserver.DTO.group;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GroupCreateDTO {

    private int idAdmin;
    private String name;
    private String profilePictureUrl;
    private List<Integer> idMembers;

}
