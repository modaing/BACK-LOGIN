package com.insider.login.webSocket.Cahtting.service;

import com.insider.login.webSocket.Cahtting.dto.MessageDTO;
import com.insider.login.webSocket.Cahtting.entity.ChatRoom;
import com.insider.login.webSocket.Cahtting.entity.Messages;
import com.insider.login.webSocket.Cahtting.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    private final ModelMapper modelMapper;

    public Messages saveMessage(MessageDTO messageDTO) {

        messageDTO.setCreatedAt(LocalDateTime.now());

        Messages message = modelMapper.map(messageDTO, Messages.class);

        return messageRepository.save(message);
    }

    public List<MessageDTO> getMessagesByRoomId(int enteredRoomId) {

        List<Messages> messages = messageRepository.findByEnteredRoomId(enteredRoomId);
        Type listType = new TypeToken<List<MessageDTO>>() {}.getType();
        List<MessageDTO> messageDTOs = modelMapper.map(messages, listType);
        return messageDTOs;
    }
}
