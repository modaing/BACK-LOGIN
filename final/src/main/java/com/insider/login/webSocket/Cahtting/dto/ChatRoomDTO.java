package com.insider.login.webSocket.Cahtting.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomDTO {

    private String name;

    @Builder
    public ChatRoomDTO(String name) {

        this.name = name;
    }

}
