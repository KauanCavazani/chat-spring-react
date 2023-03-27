package com.example.chatserver.controller;

import com.example.chatserver.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message") // /app/message
    @SendTo("/chatroom/public")
    public Message receivePublicMessage(@Payload Message message) {
        System.out.println(message.toString());
//        simpMessagingTemplate.convertAndSend("/chatroom/public", message);
        return message;
    }

    @MessageMapping("/private-message")
    public Message receivePrivateMessage(@Payload Message message) {
        System.out.println(message.toString());
        // /user/Kauan/private
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverId(), "/private", message);
        return message;
    }

}
