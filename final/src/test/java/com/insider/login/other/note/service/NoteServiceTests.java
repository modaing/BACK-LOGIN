package com.insider.login.other.note.service;

import com.insider.login.other.note.dto.NoteDTO;
import com.insider.login.other.note.entity.Note;
import com.insider.login.other.note.repository.NoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
public class NoteServiceTests {


    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteRepository noteRepository;

    @Test
    @DisplayName("쪽지 조회 테스트")
    public void selectNoteListTest() {

        // given
        int memberId = 1000;
        Integer receiverId = 1;
        Integer senderId =2;
        Pageable pageable = Pageable.unpaged();
        String deleteYn = "N";

        // when
        Page<NoteDTO> noteList = noteService.selectNoteList(memberId, receiverId, senderId, pageable, deleteYn);


        // then
        Assertions.assertNotNull(noteList);
        noteList.forEach(note -> System.out.println("noteList: " + note));

    }

    @Test
    @DisplayName("쪽지 insert 테스트")
    public void insertNote() {

        // given
        NoteDTO noteDTO = new NoteDTO(1, "2020-20-20", "제목", "내용", 2, 3, "N");

        // when
        Map<String, Object> result = new HashMap<>();

        try {
            noteService.insertNote(noteDTO);
            result.put("result", true);
            System.out.println("Check : " + result);

        } catch (Exception e) {
            noteService.insertNote(noteDTO);
            result.put("result", false);
            System.out.println("Check : " + result);
        }

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.get("result"), true);

    }

    @Test
    @DisplayName("쪽지 삭제여부 변경 test (기능 : delete 기능의 update)")
    public void deleteNoteTest() {

        // given
        int noteNo = 1;
        String deleteYn = "Y";

        // when
        Map<String, Object> result = new HashMap<>();
        result.put("result", noteService.deleteNote(noteNo, deleteYn));

        try {
            noteService.deleteNote(noteNo, deleteYn);
            result.put("result", true);
            System.out.println("Check : " + result);

        } catch (Exception e) {
            noteService.deleteNote(noteNo, deleteYn);
            result.put("result", true);
            System.out.println("Check : " + result);

        }

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.get("result"), true);

    }





}
