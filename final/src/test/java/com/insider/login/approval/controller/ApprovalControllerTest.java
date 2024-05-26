package com.insider.login.approval.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.insider.login.approval.dto.*;
import com.insider.login.approval.service.ApprovalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApprovalController.class)
public class ApprovalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApprovalService approvalService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String token = "BEARER eyJkYXRlIjoxNzE1MzI1NjIzMjk4LCJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJwb3NpdGlvbk5hbWUiOiLslYzrsJQiLCJzdWIiOiIyNDA1MDE2MjkiLCJkZXBhcnRObyI6MSwicm9sZSI6IkFETUlOIiwiaW1hZ2VVcmwiOiIxIiwibmFtZSI6IuydtOynhOyVhCIsIm1lbWJlclN0YXR1cyI6IuyerOyngSIsImV4cCI6MTcxNTQxMjAyMywiZGVwYXJ0TmFtZSI6IuyduOyCrO2MgCIsIm1lbWJlcklkIjoyNDA1MDE2Mjl9.3y1zkFUIHq8cEGHBKPqvToYWT_m9iaVvDGphqoJ2c1s";


    @DisplayName("양식 목록 조회")
    @Test
    @WithMockUser(username = "240501629")
    public void testSelectFormList() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/approvals/forms")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @DisplayName("특정 양식 조회")
    @Test
    @WithMockUser(username = "240501629")
    public void testSelectForm() throws Exception {
        //given
        String formNo = "abs";

        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/approvals/forms/{formNo}", formNo)
                        .contentType(MediaType.APPLICATION_JSON))    //요청의 content type 설정
                .andExpect(status().isOk())    //HTTP 상태코드가 200인지 확인
                .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))  //응답의 content type을 확인
                .andReturn();
    }


    @DisplayName("전자 결재 상세 조회")
    @Test
    public void TestSelectApproval() throws Exception {
        //given
        String approvalNo = "2024-con00001";

        //when
        //then
       mockMvc.perform(MockMvcRequestBuilders
               .get("/approvals/{approvalNo}", approvalNo)
                       .with(user("240501959").password("0000"))
               .contentType(MediaType.APPLICATION_JSON))    //요청의 content type 설정
               .andExpect(status().isOk())    //HTTP 상태코드가 200인지 확인
               .andExpect(content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))  //응답의 content type을 확인
               .andReturn();
    }

    @DisplayName("전자 결재 목록 조회")
    @Test
    @WithMockUser(username="240501629", roles = {"ADMIN"})
    public void testSelectApprovalList() throws Exception {
        //RequestParam = fg, page
        //memberId : ""
        // selectApprovalList(int memberId, Map<String, Object> condition, pageNo)
        //  condition.put("flag", "receivedRef");
        //  condition.put("offset", 10);
        //  condition.put("limit", 10);
        //  condition.put("title", "");
        //  condition.put("direction", "ASC");
        // /approvals?fg={approvalflag}&page={pageno}&title={title}

        //given
        String memberId = "240501629";
        Map<String, Object> condition = new HashMap<>();
        condition.put("flag", "given");
        condition.put("title", "경조금");
        int pageNo = 0;
/*

        Page<ApprovalDTO> dummyPage = new PageImpl<>(Arrays.asList(new ApprovalDTO(), new ApprovalDTO()));
        Mockito.when(approvalService.selectApprovalList(Mockito.anyInt(), Mockito.anyMap(), Mockito.anyInt()))
                        .thenReturn(dummyPage);
*/
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/approvals")
                        .param("fg", condition.get("flag").toString())
                        .param("page", String.valueOf(pageNo))
                        .param("title", condition.get("title").toString())
                        .header("memberId", memberId))
                .andExpect(status().isOk());
    }


    @DisplayName("전자 결재 기안")
    @Test
    @WithMockUser(username = "240501629")
    public void testInsertApprovalController() throws Exception {

        given(approvalService.selectApprovalNo(any(String.class)))
                .willReturn("2024-abs00013");

        ResponseDTO insertMockResponse = new ResponseDTO();
        insertMockResponse.setStatus(200);
        given(approvalService.insertApproval(any(ApprovalDTO.class), anyList()))
                .willReturn(any(ApprovalDTO.class));

        MockMultipartFile multipartFile = new MockMultipartFile("multipartFile", "image.png", MediaType.IMAGE_PNG_VALUE, "image".getBytes());

        ApprovalDTO approvalDTO = new ApprovalDTO();
        approvalDTO.setMemberId(240501629);
        approvalDTO.setFormNo("abs");
        approvalDTO.setApprovalTitle("제목");
        approvalDTO.setApprovalContent("내용");
        approvalDTO.setApprovalStatus("처리 중");

        List<ApproverDTO> approverDTOList = new ArrayList<>();
        ApproverDTO approverDTO = new ApproverDTO();
        approverDTO.setMemberId(2024001003);
        approverDTOList.add(approverDTO);
        approvalDTO.setApprover(approverDTOList);

        List<ReferencerDTO> referencerDTOList = new ArrayList<>();
        ReferencerDTO referencerDTO = new ReferencerDTO();
        referencerDTO.setMemberId(240401004);
        referencerDTOList.add(referencerDTO);
        approvalDTO.setReferencer(referencerDTOList);

        ObjectMapper objectMapper = new ObjectMapper();

        String approvalDTOJson = objectMapper.writeValueAsString(approvalDTO);

        MockMultipartFile approvalDTOFile = new MockMultipartFile(
          "approvalDTO",
          "approvalDTO.json",
          MediaType.APPLICATION_JSON_VALUE,
          approvalDTOJson.getBytes()
        );

        RequestBuilder request = MockMvcRequestBuilders.multipart("/approvals")
                .file(multipartFile)
                .file(approvalDTOFile)
                .header("memberId", "240501629")
                .header("Authorization", "BEARER eyJkYXRlIjoxNzE2NTcyNTU5NzM3LCJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJwb3NpdGlvbk5hbWUiOiLslYzrsJQiLCJzdWIiOiIyNDA1MDE2MjkiLCJkZXBhcnRObyI6MSwicm9sZSI6IkFETUlOIiwiaW1hZ2VVcmwiOiIxIiwibmFtZSI6IuydtOynhOyVhCIsIm1lbWJlclN0YXR1cyI6IuyerOyngSIsImV4cCI6MTcxNjY1ODk1OSwiZGVwYXJ0TmFtZSI6IuyduOyCrO2MgCIsIm1lbWJlcklkIjoyNDA1MDE2Mjl9.a1nOX7HFCeY8BakbQATKC9naB92QhD1FP38bo5AnEEA")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .with(csrf());

//        try {
//
//            mockMvc.perform(request)
//                    .andExpect(status().isOk())
//                    .andReturn();
//        }
//        catch (Exception e) {
//            e.getMessage();
//        }
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(200));

    }

    @DisplayName("전자결재 회수 테스트")
    @Test
    @WithMockUser(username = "240501629")
    public void testUpdateApproval() throws Exception {
        //given

        //when
        String approvalNo = "2024-abs00003";

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/approvals/{approvalNo}", approvalNo)
                .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("전자결재 결재 처리 테스트")
    @Test
    @WithMockUser(username = "240501629")
    public void testUpdateApprover() throws Exception {
        //given

        String approverNo = "2024-abs00003_apr001";
        String approverStatus = "승인";
        String rejectReason = "이러한 사유로 반려합니다.";

        ApproverDTO approverDTO = new ApproverDTO();
        approverDTO.setApproverStatus(approverStatus);
        approverDTO.setRejectReason(rejectReason);

        //when
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(approverDTO);

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/approvers/{approverNo}", approverNo)
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @DisplayName("전자결재 임시저장 삭제 테스트")
    @Test
    @WithMockUser(username = "240501629")
    public void testDeleteApproval() throws Exception {
        //given
        String approvalNo = "2024-sup00002";

        //when


        //then
        mockMvc.perform(MockMvcRequestBuilders.delete("/approvals/{approvalNo}", approvalNo)
                .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
