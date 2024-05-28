package com.insider.login.webSocket.Cahtting.dto;

import jakarta.persistence.Column;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EntRoomReqDTO {

        private int memberId;

        private int enteredRoomId;

        private int receiverId;

        private String roomName;

        private String senderDeleteYn;

        private String receiverDeleteYn;

}
