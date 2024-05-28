package com.insider.login.webSocket.Cahtting.service;

import com.insider.login.member.entity.Member;
import com.insider.login.member.repository.MemberRepository;
import com.insider.login.webSocket.Cahtting.dto.EntRoomReqDTO;
import com.insider.login.webSocket.Cahtting.entity.ChatRoom;
import com.insider.login.webSocket.Cahtting.entity.RoomStatus;
import com.insider.login.webSocket.Cahtting.repository.EnteredRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    private final EnteredRoomRepository enteredRoomRepository;

    private final MemberRepository memberRepository;

    private final ModelMapper modelMapper;

    /**
     * 방 생성
     */
    public ChatRoom save(int memberId, int receiverMemberId, String roomName, String senderDeleteYn, String receiverDeleteYn) {
        Member member = memberRepository.findById(memberId).orElse(null);
        Member receiverMember = memberRepository.findById(receiverMemberId).orElse(null);

        if (member == null || receiverMember == null) {
            System.out.println("실패 했음");
        }
        String enteredRoomName = roomName;

        senderDeleteYn = "N";
        receiverDeleteYn = "N";

        ChatRoom enteredRoom = new ChatRoom(member, receiverMember, enteredRoomName, senderDeleteYn, receiverDeleteYn);
        enteredRoomRepository.save(enteredRoom);
        return enteredRoom;

    }


    public List<EntRoomReqDTO> selectRoomList(Optional<Member> member) {
        if (member.isPresent()) {
            Member memberEntity = member.get();
            List<ChatRoom> rooms = enteredRoomRepository.findAllActiveRooms(memberEntity);

            if (rooms.isEmpty()) {
                System.out.println("No rooms found for member: " + memberEntity.getMemberId());
                return new ArrayList<>();
            }

            List<EntRoomReqDTO> roomList = new ArrayList<>();
            for (ChatRoom room : rooms) {
                EntRoomReqDTO enteredRoom = modelMapper.map(room, EntRoomReqDTO.class);
                roomList.add(enteredRoom);
            }

            System.out.println("Found rooms: " + roomList);
            return roomList;
        } else {
            System.out.println("Member not found.");
            return new ArrayList<>();
        }
    }

    /**
     * 방 삭제
     */
    public ChatRoom deleteRoom(int enteredRoomId) {
        ChatRoom enteredRoom = enteredRoomRepository.findById(enteredRoomId).orElse(null);

        if (enteredRoom != null) {
            enteredRoomRepository.delete(enteredRoom);
        }
        return enteredRoom;
    }

    /**
     * 방 삭제 (수정)
     */
    public void updateRoom(int roomId, int memberId, String action) {
        ChatRoom room = enteredRoomRepository.findById(roomId).orElse(null);
        if (room != null) {
            if (memberId == room.getMember().getMemberId()) {
                if ("delete".equalsIgnoreCase(action)) {
                    room.markSenderAsDeleted();
                } else {

                }
            } else if (memberId == room.getReceiverMember().getMemberId()) {
                if ("delete".equalsIgnoreCase(action)) {
                    room.markReceiverAsDeleted();
                } else {

                }
            }
            enteredRoomRepository.save(room);
        }
    }
}
