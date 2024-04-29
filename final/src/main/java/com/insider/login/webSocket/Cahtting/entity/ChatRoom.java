package com.insider.login.webSocket.Cahtting.entity;

import com.insider.login.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "chat_room")
public class ChatRoom extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomNumber;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 방을 만든 사람 및 대화 상대


    @Builder
    public ChatRoom(Member member) {
        this.member = member;
    }

}
