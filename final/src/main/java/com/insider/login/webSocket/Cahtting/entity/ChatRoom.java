package com.insider.login.webSocket.Cahtting.entity;

import com.insider.login.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "entered_room")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entered_room_id")
    private int enteredRoomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", referencedColumnName = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_MEMBER", referencedColumnName = "MEMBER_ID")
    private Member receiverMember;

    @Column(name = "room_name")
    private String roomName;

    @Enumerated(value = EnumType.STRING)
    private RoomStatus roomStatus = RoomStatus.ENTER;

    @Column(name = "sender_delete_yn")
    private String senderDeleteYn;

    @Column(name = "receiver_delete_Yn")
    private String receiverDeleteYn;


    public ChatRoom(Member member, Member receiverMember, String roomName, String senderDeleteYn, String receiverDeleteYn) {
        this.member = member;
        this.receiverMember = receiverMember;
        this.roomName = roomName;
        this.senderDeleteYn = senderDeleteYn;
        this.receiverDeleteYn = receiverDeleteYn;
    }

    public void markSenderAsDeleted() {
        this.senderDeleteYn = "Y";
    }

    public void markReceiverAsDeleted() {
        this.receiverDeleteYn = "Y";
    }


}
