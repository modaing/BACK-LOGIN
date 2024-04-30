package com.insider.login.approval.service;

import com.insider.login.approval.dto.ApprovalDTO;
import com.insider.login.approval.dto.ApproverDTO;
import com.insider.login.approval.dto.AttachmentDTO;
import com.insider.login.approval.dto.ReferencerDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
//@AutoConfigureMockMvc
public class ApprovalServiceTest {

    @Autowired
    private ApprovalService approvalService;

//    @Autowired
//    private MockMvc mockMvc;

    private static Stream<Arguments> newForm(){
        return Stream.of(
                Arguments.of(
                        "con",
                        "<form><div></div></form>"
                )
        );
    }




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

        *//*


        Form newForm = new Form(form_no, form_name, form_shape);

        Assertions.assertDoesNotThrow(
                () -> approvalService.insertForm(newForm)
        );
    }
*/

    @Test
    void testInsertApproval(){
        //given
        ApprovalDTO approvalDTO = new ApprovalDTO();

        List<ApproverDTO> approverList  = new ArrayList<>();
        List<ReferencerDTO> referencerList = new ArrayList<>();
        List<MultipartFile> files = new ArrayList<>();


        ApproverDTO approverDTO1 = new ApproverDTO("2024-rei00001_apr001", "2024-rei00001", 1, "승인", "2024-04-29 18:50:00", 2024001002);
        ApproverDTO approverDTO2 = new ApproverDTO("2024-rei00001_apr002", "2024-rei00001", 2, "처리 중", "2024-04-29 18:50:00", 2024001001);

        ReferencerDTO referencerDTO = new ReferencerDTO("2024-rei00001_ref001", "2024-rei00001", 2024001003, 1);

        approverList.add(approverDTO1);
        approverList.add(approverDTO2);

        referencerList.add(referencerDTO);

        approvalDTO.setApprovalNo("2024-rei00001");
        approvalDTO.setMemberId(2024001002);
        approvalDTO.setApprovalTitle("금일 연장근무 요청드립니다.");
        approvalDTO.setApprovalContent("<form name=\"form\">\n" +
                "\t\t\t\t\t\t\t<div name=\"wholeForm\"id=\"wholeForm\">\n" +
                "\t\t\t\t\t\t\t\t<div name=\"titleform\" id=\"titleform\">\n" +
                "\t\t\t\t\t\t\t\t\t<input type=\"text\" name=\"title\" id=\"title\" placeholder=\"제목\">\n" +
                "\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t<table>\n" +
                "\t\t\t\t\t\t\t\t\t<tr name=\"rei_content\" id=\"rei_content\"> \n" +
                "\t\t\t\t\t\t\t\t\t  <td colspan=\"2\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t<tr >\n" +
                "\t\t\t\t\t\t\t\t\t\t<th>휴직 시작일자</th>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td>2024-04-30</td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t<tr >\n" +
                "\t\t\t\t\t\t\t\t\t\t<th>휴직 종료일자</th>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td>2024-05-30</td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t<tr >\n" +
                "\t\t\t\t\t\t\t\t\t\t<th>복직일자</th>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td>2024-06-01</td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t<tr name=\"abs_reason\" id=\"abs_reason\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<th>복직사유</th>\n" +
                "\t\t\t\t\t\t\t\t\t\t<td>개인 질병으로 휴직하였으나 치료가 완료되어 복직하고자 합니다.</td>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t<div name=\"date\" id=\"date\">\n" +
                "\t\t\t\t\t\t\t\t<div>2024-04-29</div>\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t</form>");
        approvalDTO.setApprovalDate("2024-04-29 18:50:00");
        approvalDTO.setApprovalStatus("처리 중");
        approvalDTO.setFormNo("ovt");


            //파일 처리
            //테스트용 PDF 파일 생성
            MultipartFile pdfFile = null;
            byte[] pdfFileContent = "Test PDF Content".getBytes();
            pdfFile = new MockMultipartFile("testfile.pdf", "testfile.pdf", "application/pdf", pdfFileContent);

            //테스트용 이미지 파일 생성
            MultipartFile imgFile = null;
            byte[] imageFileContent = "Test Image Content".getBytes();
            imgFile = new MockMultipartFile("testimage.jpg", "testimage.jpg", "image/jpeg", imageFileContent);

            List<AttachmentDTO> attachmentList = new ArrayList<>();
            AttachmentDTO attachmentDTO1 = new AttachmentDTO();
            attachmentDTO1.setFileNo("2024-rei00001_f001");
            attachmentDTO1.setFileOriname(pdfFile.getOriginalFilename());
            attachmentDTO1.setFileSavename(pdfFile.getName());
            attachmentDTO1.setFileSavepath("C:/login/upload");
            attachmentDTO1.setApprovalNo("2024-rei00001");

            AttachmentDTO attachmentDTO2 = new AttachmentDTO();
            attachmentDTO2.setFileNo("2024-rei00001_f002");
            attachmentDTO2.setFileOriname(imgFile.getOriginalFilename());
            attachmentDTO2.setFileSavename(imgFile.getName());
            attachmentDTO2.setFileSavepath("C:/login/upload");
            attachmentDTO2.setApprovalNo("2024-rei00001");

            attachmentList.add(attachmentDTO1);
            attachmentList.add(attachmentDTO2);

            //파일 업로드 서비스에 테스트용 PDF 파일 전달

        approvalDTO.setAttachment(attachmentList);

        approvalDTO.setApprover(approverList);
        approvalDTO.setReferencer(referencerList);

        //when
//        approvalService.insertApproval(approvalDTO);

        //then
        Assertions.assertDoesNotThrow(
                () -> approvalService.insertApproval(approvalDTO)
        );

    }


}
