package com.example.chatserver.controller;

import com.example.chatserver.DTO.chat.ChatDTO;
import com.example.chatserver.model.Message;
import com.example.chatserver.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/chats")
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ChatService service;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receivePublicMessage(@Payload Message message) {
        System.out.println(message.toString());
        return message;
    }

    @MessageMapping("/private-message")
    public Message receivePrivateMessage(@Payload Message message) {
        System.out.println(message.toString());
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverId(), "/private", message);
        return message;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatDTO> getChats(@PathVariable int id) {
        ChatDTO chats = service.getChats(id);
        if (!chats.getGroups().isEmpty() && !chats.getUsers().isEmpty()) {
            return ResponseEntity.status(200).body(chats);
        }

        return ResponseEntity.status(204).body(chats);
    }

}
