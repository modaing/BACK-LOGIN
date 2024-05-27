package com.insider.login.webSocket.Cahtting.repository;

import com.insider.login.webSocket.Cahtting.dto.MessageDTO;
import com.insider.login.webSocket.Cahtting.entity.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Messages, Integer> {

    @Query("SELECT m FROM Messages m WHERE m.enteredRoomId = :enteredRoomId")
    List<Messages> findByEnteredRoomId(@Param("enteredRoomId") int enteredRoomId);
}
