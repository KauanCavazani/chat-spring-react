package com.example.chatserver.controller;

import com.example.chatserver.DTO.UserInfoDTO;
import com.example.chatserver.DTO.UserRegisterLoginDTO;
import com.example.chatserver.mapper.UserMapper;
import com.example.chatserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private UserMapper mapper;

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody UserRegisterLoginDTO newUserDTO) {
        if (service.register(newUserDTO) != null) {
            return ResponseEntity.status(201).build();
        }

        return ResponseEntity.status(400).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserInfoDTO> login(@RequestBody UserRegisterLoginDTO userDTO) {
        UserInfoDTO userLoginDTO = service.login(userDTO);
        if (userLoginDTO != null) {
            return ResponseEntity.status(200).body(userLoginDTO);
        }

        return ResponseEntity.status(400).build();
    }

}
