package com.insider.login.webSocket.Cahtting.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String userName;

    private String msg;

    private String imageUrl;

    private int roomNumber;

    private String createdDate;


    @Builder //생성자 빌드
    public ChatMessage(String msg, String imageUrl, int roomNumber, String userName) {
        this.msg = msg;
        this.imageUrl = imageUrl;
        this.roomNumber=roomNumber;
        this.userName=userName;
        this.createdDate = ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

}