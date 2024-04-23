package com.insider.login.other.note;


import com.insider.login.other.note.dto.NoteDTO;
import com.insider.login.other.note.entity.Note;
import com.insider.login.other.note.repository.NoteRepository;
import com.insider.login.other.note.service.NoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
public class NoteTests {


    NoteService noteService = Mockito.mock(NoteService.class);

    // Mock noteRepository
    NoteRepository noteRepository = Mockito.mock(NoteRepository.class);

    // Mock modelMapper
    ModelMapper modelMapper = Mockito.mock(ModelMapper.class);



    // 가짜 Note 리스트 생성 (테스트용)
    private List<Note> createMockNoteList() {

        List<Note> noteList = new ArrayList<>();

        noteList.add(new Note(1, "2020-20-20", "TITLE1", "CONTENT1", 1010, 2222, "N"));
        noteList.add(new Note(2, "2020-20-20", "TITLE2", "CONTENT2", 1011, 2223, "N"));
        noteList.add(new Note(3, "2020-20-20", "TITLE3", "CONTENT3", 1012, 2224, "N"));
        noteList.add(new Note(4, "2020-20-20", "TITLE4", "CONTENT4", 1013, 2225, "N"));
        noteList.add(new Note(5, "2020-20-20", "TITLE5", "CONTENT5", 1014, 2226, "N"));

        return noteList;
    }

    @DisplayName("전체 쪽지에 대한 리스트 조회")
    @Test
    void findNoteListTest() {

        // 가짜 Note 리스트 생성
        List<Note> noteList = createMockNoteList();


        // findById() 메서드가 호출됐을 때 반환할 객체 설정
        when(noteRepository.findById(1)).thenReturn(Optional.of(noteList.get(0))); // 여기서 첫 번째 Note를 반환하도록 설정

        // findById() 메서드 호출
        Optional<Note> result = noteRepository.findById(1);

        // 결과 확인
        assertTrue(result.isPresent());
        assertEquals(noteList.get(0), result.get()); // 첫 번째 Note와 반환된 결과를 비교

        // 데이터 출력
        System.out.println("Note List:");
        noteList.forEach(System.out::println);
    }

    @DisplayName("페이징 조회 테스트")
    @Test
    void pagingTest() {


        //given
        int offset = 10;     // 조회를 건너뛸 행 수
        int limit = 5;       // 조회할 최대 행 수
        Pageable pageable = PageRequest.of(offset, limit);

        //when
        Page<Note> fakePage = mock(Page.class);
        when(fakePage.getContent()).thenReturn(createMockNoteList()); // 가짜 Note 리스트 반환
        when(noteRepository.findAll(pageable)).thenReturn(fakePage);

        //then
        // 페이징된 데이터 가져오기
        Page<Note> resultPage = noteRepository.findAll(pageable);

        // 페이징 결과 검증
        assertEquals(limit, resultPage.getContent().size()); // 페이징된 데이터의 크기가 리미트와 같은지 확인
    }


    @Test
    @DisplayName("INSERT 테스트")
    void testInsertNote() {

        // given 가짜 Note 리스트 생성
        List<Note> noteList = createMockNoteList();

        NoteDTO noteDTO = new NoteDTO();
        noteDTO.setNoteTitle("테스트 제목");
        noteDTO.setNoteContent("테스트 본문");
        noteDTO.setSendNoteDate("2020-20-20");
        noteDTO.setReceiverId(11111);
        noteDTO.setSenderId(99999);
        noteDTO.setDeleteYn("N");

        // 엔티티로 변환
        Note note = modelMapper.map(noteDTO, Note.class);

        // save() 메서드가 호출됐을 때 반환할 객체 설정
        when(noteRepository.save(any(Note.class))).thenAnswer(invocation -> {
            Note savedNote = invocation.getArgument(0);

            return savedNote;
        });

        // when save() 메서드 호출
        Note savedNote = noteRepository.save(new Note(noteDTO));

        // then 결과 검증
        assertNotNull(savedNote);
        assertEquals(noteDTO.getNoteTitle(), savedNote.getNoteTitle());
        assertEquals(noteDTO.getNoteContent(), savedNote.getNoteContent());

        // 데이터 출력
        System.out.println("Saved Note:");
        System.out.println(savedNote);
    }

    @Test
    @DisplayName("UPDATE 테스트1 -> 선택한 쪽지 id 가져오기")
    void testUpdateTest1() {

        // given 가짜 Note 리스트 생성
        List<Note> noteList = createMockNoteList();

        // findById() 메서드가 호출됐을 때 반환할 객체 설정
        when(noteRepository.findById(1)).thenReturn(Optional.of(noteList.get(0)));

        // findById() 메서드 호출
        Optional<Note> result = noteRepository.findById(1);


        // then 결과 검증
        assertNotNull(result);


        // 데이터 출력
        System.out.println("Find ID(1) Note:");
        System.out.println(result);

    }

    @Test
    @DisplayName("UPDATE 테스트2 -> 선택한 쪽지의 deleteYn 값을 변경")
    void testUpdateTest2() {
        // given
        List<Note> noteList = createMockNoteList();
        Note note = noteList.get(0); // 첫 번째 노트를 선택

        // findById() 메서드가 호출됐을 때 반환할 객체 설정
        when(noteRepository.findById(1)).thenReturn(Optional.of(note));

        // when
        Optional<Note> result = noteRepository.findById(1);
        if (result.isPresent()) {

            Note selectedNote = new Note(
                    result.get().getNoteNo(),
                    result.get().getSendNoteDate(),
                    result.get().getNoteTitle(),
                    result.get().getNoteContent(),
                    result.get().getSenderId(),
                    result.get().getReceiverId(),
                    "Y" // deleteYn 값 변경
            );

            // save() 메서드가 호출될 때 반환할 값을 설정
            when(noteRepository.save(any(Note.class))).thenReturn(selectedNote);
        }

        // save() 메서드 호출
        Note updatedNote = noteRepository.save(new Note());

        // 결과 검증
        assertNotNull(updatedNote);
        assertEquals("Y", updatedNote.getDeleteYn());

        // 데이터 출력
        System.out.println("Updated Note:");
        System.out.println(updatedNote);
    }





}


