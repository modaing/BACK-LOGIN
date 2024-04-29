package com.insider.login.webSocket.Cahtting.dto;

import com.insider.login.webSocket.Cahtting.entity.ChatMessage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//채팅 메시지 create
//발송자, 메시지, 채팅방 정보가 담겨 요청
@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDTO {


    public enum MessageType{
        ENTER, TALK
    }

    private String userName;

    private String msg; //msg

    private int roomNumber;


    //생성자
    @Builder
    public ChatMessageDTO(String userName, String msg, int roomNumber) {
        this.msg = msg;
        this.userName = userName;
        this.roomNumber=roomNumber;

    }
    //객체 만들기
    public ChatMessage toEntity() {
        return ChatMessage.builder()
                .userName(userName)
                .msg(msg)
                .roomNumber(roomNumber)
                .build();
    }

}
