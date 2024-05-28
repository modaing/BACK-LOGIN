package com.insider.login.webSocket.Cahtting.repository;

import com.insider.login.member.entity.Member;
import com.insider.login.webSocket.Cahtting.entity.ChatRoom;
import com.insider.login.webSocket.Cahtting.entity.RoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnteredRoomRepository extends JpaRepository<ChatRoom, Integer> {

    @Query("SELECT e FROM ChatRoom e WHERE (e.member = :member OR e.receiverMember = :member) AND (e.senderDeleteYn = 'N' OR e.receiverDeleteYn = 'N')")
    List<ChatRoom> findAllActiveRooms(@Param("member") Member member);

}
