package com.example.chatserver.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {

    private String senderId;
    private String receiverId;
    private String message;
    private String date;
    private Status status;

}
