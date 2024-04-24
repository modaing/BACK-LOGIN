package com.insider.login.other.note.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.insider.login.common.CommonController;
import com.insider.login.other.note.dto.NoteDTO;
import com.insider.login.other.note.entity.Note;
import com.insider.login.other.note.repository.NoteRepository;
import org.junit.Assume;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class NoteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteRepository noteRepository;

    @Test
    @DisplayName("컨트롤러 쪽지 조회 테스트")
    public void testSelectNoteList() throws Exception {

        // given
        int memberNo = 12345;
        int receiverId = 12345;
        String deleteYn = "N";
        int page = 0;
        int size = 10;
        String sort = "noteNo";
        String direction = "DESC";

        // 노트가 존재하는지 확인
        Page<Note> noteList = noteRepository.findByReceiverIdAndDeleteYn(memberNo, PageRequest.of(page, size), deleteYn);
        Assume.assumeTrue(!noteList.isEmpty()); // 노트가 없는 경우에는 테스트를 스킵

        // when
        MvcResult result = mockMvc.perform(get("/members/{memberNo}/notes", memberNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("receiverId", String.valueOf(receiverId))
                        .param("deleteYn", deleteYn)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", sort)
                        .param("direction", direction))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatusCode").value(200))
                .andExpect(jsonPath("$.message").value("조회 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        // then
        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }

    @Test
    @DisplayName("컨트롤러 쪽지 insert 테스트")
    public void testInsertNote() throws Exception {

        // given
        NoteDTO noteDTO = new NoteDTO(2, "2020-20-20", "제목", "내용", 2, 3, "N");

        // when
        MvcResult result = mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(noteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("조회 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();


        // then
        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }

    @Test
    @DisplayName("deleteYn을 이용한 쪽지 삭제여부 update 테스트")
    public void testDeleteNote() throws Exception {
        // given
        int noteNo = 1;
        String deleteYn = "Y";

        // when
        MvcResult result = mockMvc.perform(put("/notes/{noteNo}", noteNo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("deleteYn", deleteYn))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatusCode").value(200))
                .andExpect(jsonPath("$.message").value("조회 성공"))
                .andExpect(jsonPath("$.results").exists())
                .andReturn();

        // then
        String content = result.getResponse().getContentAsString();
        System.out.println("Response Content: " + content);
    }





}


