package com.insider.login.webSocket.Cahtting.dto;

import com.insider.login.webSocket.Cahtting.entity.ChatRoom;
import lombok.Getter;

@Getter
public class ChatRoomResDTO {

    // 응답 DTO

    private int roomNumber;

    private String nickname;

    private String msg;

    private String messageCreatedDate;

    public ChatRoomResDTO(ChatRoom chatRoom, String msg, String messageCreatedDate) {
        this.roomNumber = chatRoom.getRoomNumber();
        this.nickname = chatRoom.getMember().getName(); // 방을 만든 사람의 이름 가져오기
        this.msg = msg;
        this.messageCreatedDate = messageCreatedDate;
    }

}
