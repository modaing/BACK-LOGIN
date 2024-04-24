package com.insider.login.other.announce.cotroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insider.login.common.CommonController;
import com.insider.login.other.announce.dto.AncFileDTO;
import com.insider.login.other.announce.dto.AnnounceDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AnnounceControllerTests {


    @Autowired
    private MockMvc mockMvc;

    CommonController controller = new CommonController();

    @Test
    @DisplayName("공지사항 + 파일 insert 테스트")
    public void testInsertAnc() throws Exception {
        // given
        AnnounceDTO announceDTO = new AnnounceDTO();
        announceDTO.setAncNo(30);
        announceDTO.setAncContent("내용");
        announceDTO.setAncTitle("제목");
        announceDTO.setAncDate("2020-11-11");
        announceDTO.setHits(0);
        announceDTO.setAncWriter("김고무");
        System.out.println(announceDTO);

        // JSON 데이터를 문자열로 변환하여 파일에 추가
        String json = new ObjectMapper().writeValueAsString(announceDTO);
        MockMultipartFile jsonFile = new MockMultipartFile("announceDTO", "",
                MediaType.APPLICATION_JSON_VALUE, json.getBytes());

        // 테스트할 파일 생성
        MockMultipartFile files = new MockMultipartFile("files", "test.txt",
                MediaType.TEXT_PLAIN_VALUE, "test file content".getBytes());

        // when
        // mockMvc를 사용하여 요청 전송
        mockMvc.perform(multipart("/announce")
                        .file(jsonFile)
                        .file(files)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());


    }

}
