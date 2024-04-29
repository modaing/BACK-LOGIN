package com.insider.login.webSocket.Cahtting.controller;

import com.insider.login.member.repository.MemberRepository;
import com.insider.login.webSocket.Cahtting.dto.ChatRoomResDTO;
import com.insider.login.webSocket.Cahtting.repository.ChatRoomRepository;
import com.insider.login.webSocket.Cahtting.service.ChatMessageService;
import com.insider.login.webSocket.Cahtting.service.ChatRoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatting")
@Transactional
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageService chatMessageService;

    private final MemberRepository memberRepository;

    //전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<ChatRoomResDTO>> findAll() {
        List<ChatRoomResDTO> roomResponseDTO =  chatRoomService.findByList();
        return ResponseEntity.ok(roomResponseDTO);
    }

}
