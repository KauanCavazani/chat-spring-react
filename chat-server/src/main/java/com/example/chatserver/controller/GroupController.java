package com.example.chatserver.controller;

import com.example.chatserver.DTO.group.GroupCreateDTO;
import com.example.chatserver.DTO.group.GroupDTO;
import com.example.chatserver.model.Group;
import com.example.chatserver.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService service;

    @PostMapping
    public ResponseEntity<Group> createGroup(GroupCreateDTO groupCreateDTO) {
        System.out.println(groupCreateDTO.toString());
        Group groupCreated = service.create(groupCreateDTO);
        return ResponseEntity.status(201).body(groupCreated);
    }

    @PostMapping("/member")
    public ResponseEntity<Void> addMember(GroupDTO groupDTO) {
        if (service.addMember(groupDTO)) {
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(400).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Group>> getByIdMember(@PathVariable int id) {
        List<Group> groups = service.getByIdMember(id);
        if (!groups.isEmpty()) {
            return ResponseEntity.status(200).body(groups);
        }

        return ResponseEntity.status(204).body(groups);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(GroupDTO groupDTO) {
        if (service.deleteMember(groupDTO)) {
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(400).build();
    }

    @DeleteMapping("/leave")
    public ResponseEntity<Void> leaveGroup(GroupDTO groupDTO) {
        if (service.leave(groupDTO)) {
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(400).build();
    }

}
