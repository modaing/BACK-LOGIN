package com.insider.login.webSocket.Cahtting.service;

import com.insider.login.member.repository.MemberRepository;
import com.insider.login.webSocket.Cahtting.dto.ChatRoomResDTO;
import com.insider.login.webSocket.Cahtting.entity.ChatMessage;
import com.insider.login.webSocket.Cahtting.entity.ChatRoom;
import com.insider.login.webSocket.Cahtting.repository.ChatMessageRepository;
import com.insider.login.webSocket.Cahtting.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;// 채팅방조회

    private final MemberRepository memberRepository;

    private final ChatMessageRepository chatMessageRepository;

    /** 전체 체팅방 목록 조회 */
    public List<ChatRoomResDTO> findByList() {
        return chatRoomRepository.findAll().stream()
                .map(chatRoom -> {ChatMessage lastMessage = chatMessageRepository.findTopByRoomNumberOrderByCreatedDateDesc(chatRoom.getRoomNumber());
                    if (lastMessage!=null) {
                        return new ChatRoomResDTO(chatRoom, lastMessage.getMsg(), lastMessage.getCreatedDate());
                    }
                    else {
                        return new ChatRoomResDTO(chatRoom, "이 채팅방에 아직 메시지가 없습니다", "00:00:00");
                    }
                })
                .collect(Collectors.toList());
    }






}
