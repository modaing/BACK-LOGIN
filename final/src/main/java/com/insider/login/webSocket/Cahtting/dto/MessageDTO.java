package com.insider.login.webSocket.Cahtting.dto;

import com.insider.login.webSocket.Cahtting.entity.ChatRoom;
import lombok.*;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessageDTO implements java.io.Serializable {

    private int messageId;

    private int senderId;

    private String senderName;

    private String message;

    private int enteredRoomId;

    private LocalDateTime createdAt;

}
