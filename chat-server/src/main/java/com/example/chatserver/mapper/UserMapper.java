package com.example.chatserver.mapper;

import com.example.chatserver.DTO.UserInfoDTO;
import com.example.chatserver.DTO.UserRegisterLoginDTO;
import com.example.chatserver.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUser(UserRegisterLoginDTO userDto) {
        return new User(
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getProfilePictureUrl()
        );
    }

    public UserInfoDTO toUserInfoDTO(User user) {
        return new UserInfoDTO(
                user.getId(),
                user.getUsername(),
                user.getProfilePictureUrl(),
                user.isOnline()
        );
    }

}
