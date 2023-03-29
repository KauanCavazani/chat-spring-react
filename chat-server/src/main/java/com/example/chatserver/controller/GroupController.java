package com.example.chatserver.controller;

import com.example.chatserver.DTO.group.GroupCreateDTO;
import com.example.chatserver.DTO.group.GroupResponseDTO;
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
    public ResponseEntity<GroupResponseDTO> create(@RequestBody GroupCreateDTO groupCreateDTO) {
        GroupResponseDTO groupCreated = service.create(groupCreateDTO);
        return ResponseEntity.status(201).body(groupCreated);
    }

    @PostMapping("/{idGroup}/{idAdmin}/{idMember}")
    public ResponseEntity<Void> addMember(
            @PathVariable int idGroup,
            @PathVariable int idAdmin,
            @PathVariable int idMember
    ) {
        if (service.addMember(idGroup, idAdmin, idMember)) {
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(400).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<GroupResponseDTO>> getByIdMember(@PathVariable int id) {
        List<GroupResponseDTO> groups = service.getByIdMember(id);
        if (!groups.isEmpty()) {
            return ResponseEntity.status(200).body(groups);
        }

        return ResponseEntity.status(204).body(groups);
    }

    @DeleteMapping("/{idGroup}/{idAdmin}/{idMember}")
    public ResponseEntity<Void> deleteMember(
            @PathVariable int idGroup,
            @PathVariable int idAdmin,
            @PathVariable int idMember
    ) {
        if (service.deleteMember(idGroup, idAdmin, idMember)) {
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(400).build();
    }

    @DeleteMapping("/{idGroup}/{idMember}")
    public ResponseEntity<Void> leave(
            @PathVariable int idGroup,
            @PathVariable int idMember
    ) {
        if (service.leave(idGroup, idMember)) {
            return ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(400).build();
    }

}
