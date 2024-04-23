package com.insider.login.other.note;


import com.insider.login.other.note.dto.NoteDTO;
import com.insider.login.other.note.entity.Note;
import com.insider.login.other.note.repository.NoteRepository;
import com.insider.login.other.note.service.NoteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


@SpringBootTest
public class NoteTests {

    @Autowired
    private NoteRepository noteRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private NoteService noteService;

    @Test
    public void testSelectNoteList() {

        // Mock 데이터 설정
        int memberId = 1; // 받는 사람의 ID
        String deleteYn = "N"; // 삭제 여부
        Pageable pageable = PageRequest.of(0, 10); // 페이지 정보 (페이지 번호, 페이지 크기)

        // 테스트용 Note 객체 리스트 생성
        List<Note> noteListReceiver = new ArrayList<>(); // 받는 사람으로부터 온 쪽지 리스트
        noteListReceiver.add(new Note(1, "2024-04-12", "타이틀 1", " 내용 1", 1111, 2222,"N")); // 첫 번째 Note
        noteListReceiver.add(new Note(2, "2024-04-12", "타이틀 2", " 내용 3", 1111, 1234,"N")); // 두 번째 Note

        List<Note> noteListSender = new ArrayList<>(); // 보내는 사람으로부터 온 쪽지 리스트
        noteListSender.add(new Note(3, "2024-04-12", "타이틀 3", " 내용 3", 1111, 1010,"N")); // 세 번째 Note
        noteListSender.add(new Note(4, "2024-04-12", "타이틀 4", " 내용 4", 3333, 1010,"N")); // 네 번째 Note

        // 테스트용 Page<Note> 객체 생성
        Page<Note> notePageReceiver = new PageImpl<>(noteListReceiver);
        Page<Note> notePageSender = new PageImpl<>(noteListSender);

        // noteRepository를 mock으로 만들기
        NoteRepository noteRepository = Mockito.mock(NoteRepository.class);

        // when을 사용하여 findByReceiverIdAndDeleteYn 메서드에 대한 스텁 설정
        when(noteRepository.findByReceiverIdAndDeleteYn(eq(1), any(Pageable.class), eq(deleteYn))).thenReturn(notePageReceiver);
        when(noteRepository.findBySenderIdAndDeleteYn(eq(1), any(Pageable.class), eq(deleteYn))).thenReturn(notePageSender);

        // 서비스 메서드 호출
        Page<NoteDTO> resultReceiver = noteService.selectNoteList(memberId, null, 2222, pageable, deleteYn); // 받은 쪽지 리스트 조회
        Page<NoteDTO> resultSender = noteService.selectNoteList(memberId, 1111, null, pageable, deleteYn); // 보낸 쪽지 쪽지 리스트 조회

        // 결과 검증
        assertNotNull(resultReceiver);
        assertNotNull(resultSender);

        // 받는 쪽지 리스트 출력
        System.out.println("받는 쪽지 리스트 출력:");
        for (Note note : notePageReceiver.getContent()) {
            System.out.println("Note 정보: " + note);
        }

        // 보낸 쪽지 리스트 출력
        System.out.println("보낸 쪽지 리스트 출력:");
        for (Note note : notePageSender.getContent()) {
            System.out.println("Note 정보: " + note);
        }

    }




}


