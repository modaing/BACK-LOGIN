package com.insider.login.webSocket.Cahtting.controller;

import com.insider.login.webSocket.Cahtting.dto.MessageDTO;
import com.insider.login.webSocket.Cahtting.entity.ChatRoom;
import com.insider.login.webSocket.Cahtting.entity.Messages;
import com.insider.login.webSocket.Cahtting.service.ChatRoomService;
import com.insider.login.webSocket.Cahtting.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatRoomService chatRoomService;

    @PostMapping("/")
    public ResponseEntity<Messages> saveMessage(@RequestBody MessageDTO messageDTO) {

        Messages savedMessage = messageService.saveMessage(messageDTO);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/{enteredRoomId}")
    public List<MessageDTO> getMessagesByRoomId(@PathVariable("enteredRoomId") int enteredRoomId) {

        return messageService.getMessagesByRoomId(enteredRoomId);
    }


}
