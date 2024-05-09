package com.insider.login.approval.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insider.login.approval.dto.ApprovalDTO;
import com.insider.login.approval.dto.ApproverDTO;
import com.insider.login.approval.dto.AttachmentDTO;
import com.insider.login.approval.dto.ReferencerDTO;
import com.insider.login.approval.service.ApprovalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(ApprovalController.class)
public class ApprovalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApprovalService approvalService;

    private final ObjectMapper objectMapper = new ObjectMapper();


/*
    @DisplayName("폼 양식 추가")
    @ParameterizedTest
    @MethodSource("newForm")
    public void testInsertForm(String form_no, String form_shape) {
        */
/*String requestBody = "{\"form_no\": \"con\", \"form_shape\": \"<form><div></div></form>\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/approval/")
                .contentType("application/x-www-form-urlencoded")
                .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        */
    /*


        Form newForm = new Form(form_no, form_name, form_shape);

        Assertions.assertDoesNotThrow(
                () -> approvalService.insertForm(newForm)
        );
    }
*/

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
               .andExpect(MockMvcResultMatchers.status().isOk())    //HTTP 상태코드가 200인지 확인
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.parseMediaType("application/json;charset=UTF-8")))  //응답의 content type을 확인
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
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/approvals")
                        .param("fg", condition.get("flag").toString())
                        .param("page", String.valueOf(pageNo))
                        .param("title", condition.get("title").toString())
                        .header("memberId", memberId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        //then

    }


    @DisplayName("전자 결재 기안")
    @Test
    public void testInsertApprovalController() throws Exception {
        //given

        String memberId = "240501629";
        List<MockMultipartFile> multipartFiles = new ArrayList<>();
        List<AttachmentDTO> attachmentDTO = new ArrayList<>();
        List<ApproverDTO> approverList = new ArrayList<>();
        List<ReferencerDTO> referencerList = new ArrayList<>();

        ApproverDTO approverDTO = new ApproverDTO();
        approverDTO.setMemberId(240401004);

        ReferencerDTO referencerDTO = new ReferencerDTO();
        referencerDTO.setMemberId(2024001001);

        approverList.add(approverDTO);

        referencerList.add(referencerDTO);


        MockMultipartFile file1 = new MockMultipartFile("file1", "test.txt", MediaType.TEXT_PLAIN_VALUE, "TEST".getBytes());


        byte[] pdfContent = "PDF content".getBytes();
        MockMultipartFile file2 = new MockMultipartFile("file2", "test.pdf", "application/pdf", pdfContent);

        multipartFiles.add(file1);
        multipartFiles.add(file2);

        ApprovalDTO approvalDTO = new ApprovalDTO();
        approvalDTO.setApprovalTitle("휴직 신청합니다.");
        approvalDTO.setFormNo("abs");
        approvalDTO.setApprovalContent("<form name=\"form\">\n" +
                "\t\t\t\t\t\t\t<div name=\"wholeForm\"id=\"wholeForm\">\n" +
                "\t\t\t\t\t\t\t<div name=\"titleform\" id=\"titleform\">\n" +
                "\t\t\t\t\t\t\t  \n" +
                "\t\t\t\t\t\t\t\t<input type=\"text\" name=\"title\" id=\"title\" placeholder=\"제목\">\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t<table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr >\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t<th>휴직 시작일자</th>\n" +
                "\t\t\t\t\t\t\t\t\t  <td>2024-05-10</td>\n" +
                "\t\t\t\t\t\t\t\t  </tr>\n" +
                "\t\t\t\t\t\t\t\t  <tr >\n" +
                "\t\t\t\t\t\t\t\t\t\t\t  <th>휴직 종료일자</th>\n" +
                "\t\t\t\t\t\t\t\t\t<td>2024-06-10</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t<tr >\n" +
                "\t\t\t\t\t\t\t\t\t\t\t  <th>복직 예정일자</th>\n" +
                "\t\t\t\t\t\t\t\t\t<td>2024-06-11</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr name=\"abs_reason\" id=\"abs_reason\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t  <th>휴직사유</th>\n" +
                "\t\t\t\t\t\t\t\t\t<td>개인질병으로 인한 입원</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr name=\"orders\" id=\"orders\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t  <th>기타사항</th>\n" +
                "\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t<tr >\n" +
                "\t\t\t\t\t\t\t\t\t\t\t  <th>연락처</th>\n" +
                "\t\t\t\t\t\t\t\t\t<td>010-1234-5678</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t   </table>\n" +
                "\t\t\t\t\t\t\t </div>\n" +
                "\t\t\t\t\t\t  <div name=\"date\" id=\"date\">\n" +
                "\t\t\t\t\t\t\t<div></div>\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t</form>");

       approvalDTO.setApprovalStatus("처리 중");
       approvalDTO.setApprover(approverList);
       approvalDTO.setReferencer(referencerList);


       mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.POST,"/approvals")
               .file(multipartFiles.get(0))
               .file(multipartFiles.get(1))
               .contentType(MediaType.MULTIPART_FORM_DATA)
               .content(objectMapper.writeValueAsString(approvalDTO))
               .header("memberId", memberId))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(print());


       /* //when
        verify(approvalService).insertApproval(any(ApprovalDTO.class), anyList());
        //then
            mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.POST,"/approvals")
                    .file(file1)
                    .file(file2)
                    .param("approvalDTO.ApprovalTitle", approvalDTO.getApprovalTitle())
                    .param("approvalDTO.ApprovalContent", approvalDTO.getApprovalContent())
                    .param("approvalDTO.FormNo", approvalDTO.getFormNo())
                    .param("approvalDTO.Approver[0].memberId", String.valueOf(approvalDTO.getApprover().get(0).getMemberId()))
                    .param("approvalDTO.Referencer[0].memberId", String.valueOf(approvalDTO.getReferencer().get(0).getMemberId()))
                            .header("memberId", memberId))

                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());*/

    }
}
