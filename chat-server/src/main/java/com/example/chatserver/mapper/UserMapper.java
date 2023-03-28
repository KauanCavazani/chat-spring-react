package com.example.chatserver.mapper;

import com.example.chatserver.DTO.user.UserInfoDTO;
import com.example.chatserver.DTO.user.UserRegisterLoginDTO;
import com.example.chatserver.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public List<UserInfoDTO> toUserInfoListDTO(List<User> users) {
        List<UserInfoDTO> usersInfoDTO = new ArrayList<>();
        for (User user : users) {
            usersInfoDTO.add(toUserInfoDTO(user));
        }

        return usersInfoDTO;
    }

}
