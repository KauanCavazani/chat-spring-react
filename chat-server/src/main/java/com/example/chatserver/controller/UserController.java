package com.example.chatserver.controller;

import com.example.chatserver.DTO.user.UserInfoDTO;
import com.example.chatserver.DTO.user.UserRegisterLoginDTO;
import com.example.chatserver.mapper.UserMapper;
import com.example.chatserver.model.User;
import com.example.chatserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private UserMapper mapper;

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody UserRegisterLoginDTO newUserDTO) {
        User newUser = mapper.toUser(newUserDTO);
        if (service.register(newUser) != null) {
            return ResponseEntity.status(201).build();
        }

        return ResponseEntity.status(400).build();
    }

    @PatchMapping
    public ResponseEntity<UserInfoDTO> login(@RequestBody UserRegisterLoginDTO userLoginDTO) {
        User userResponse = service.login(mapper.toUser(userLoginDTO));
        if (userResponse != null) {
            return ResponseEntity.status(200).body(mapper.toUserInfoDTO(userResponse));
        }

        return ResponseEntity.status(400).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> logoff(@PathVariable int id) {
        if (service.logoff(id)) {
            return  ResponseEntity.status(200).build();
        }

        return ResponseEntity.status(400).build();
    }

    @GetMapping
    public ResponseEntity<List<UserInfoDTO>> getUsers() {
        List<UserInfoDTO> usersInfoDTO = mapper.toUserInfoListDTO(service.getUsers());
        if (!usersInfoDTO.isEmpty()) {
            return ResponseEntity.status(200).body(usersInfoDTO);
        }

        return ResponseEntity.status(204).body(usersInfoDTO);
    }

}
