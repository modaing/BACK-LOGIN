package com.insider.login.webSocket.Cahtting.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name= "messages")
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int messageId;

    @Column(name = "sender_id")
    private int senderId;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "message")
    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JoinColumn(name = "entered_room_id")
    private int enteredRoomId;


}
