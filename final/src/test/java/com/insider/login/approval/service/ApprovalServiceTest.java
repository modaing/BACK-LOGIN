package com.insider.login.approval.service;

import com.insider.login.approval.dto.ApprovalDTO;
import com.insider.login.approval.dto.ApproverDTO;
import com.insider.login.approval.dto.AttachmentDTO;
import com.insider.login.approval.dto.ReferencerDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    //전자결재 기안 테스트
    @Test
    void testInsertApproval(){
        //given
        ApprovalDTO approvalDTO = new ApprovalDTO();

        List<ApproverDTO> approverList  = new ArrayList<>();
        List<ReferencerDTO> referencerList = new ArrayList<>();
        List<MultipartFile> files = new ArrayList<>();


        ApproverDTO approverDTO1 = new ApproverDTO("2024-sup00002_apr001", "2024-sup00002", 1, "처리 중", "2024-04-30 13:11:00", 2024001002);
        ApproverDTO approverDTO2 = new ApproverDTO("2024-sup00002_apr002", "2024-sup00002", 2, "처리 중", "2024-04-30 13:11:00", 240401004);

        ReferencerDTO referencerDTO = new ReferencerDTO("2024-sup00002_ref001", "2024-sup00002", 2024001003, 1);

        approverList.add(approverDTO1);
        approverList.add(approverDTO2);

        referencerList.add(referencerDTO);

        approvalDTO.setApprovalNo("2024-sup00002");
        approvalDTO.setMemberId(2024001002);
        approvalDTO.setApprovalTitle("비품 신청합니다.");
        approvalDTO.setApprovalContent("<form name=\"form\">\n" +
                "\t\t\t\t\t\t\t<div name=\"wholeForm\" id=\"wholeForm\">\n" +
                "\t\t\t\t\t\t\t\t<div name=\"titleform\" id=\"titleform\">\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t<input type=\"text\" name=\"title\" id=\"title\" placeholder=\"제목\">\n" +
                "\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t<table name=\"sup_table\" id=\"sup_table\" >\n" +
                "\t\t\t\t\t\t\t\t\t<tr name=\"sup_header\" id=\"sup_header\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<th rowspan=\"2\">품명</th>\n" +
                "\t\t\t\t\t\t\t\t\t\t<th rowspan=\"2\">규격</th>\n" +
                "\t\t\t\t\t\t\t\t\t\t<th rowspan=\"2\">수량</th>\n" +
                "\t\t\t\t\t\t\t\t\t\t<th name=\"purchasePrice\" class=\"purchasePrice\" colspan=\"2\">구매예정가격</th>\n" +
                "\t\t\t\t\t\t\t\t\t\t<th rowspan=\"2\">용도</th>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t<tr name=\"purchasePricetr\" id=\"purchasePricetr\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<th name=\"purchasePrice\" class=\"purchasePrice\">단가</th>\n" +
                "\t\t\t\t\t\t\t\t\t\t<th name=\"purchasePrice\" class=\"purchasePrice\">금액</th>\n" +
                "\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_itemname\">삼성 노트북 S1200 49</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td>17인치</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_itemAmount\">1</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_price\">1400000</td> \n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_price\">업무 보조</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_itemname\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_itemAmount\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_price\"></td> \n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_price\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_itemname\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_itemAmount\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_price\"></td> \n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_price\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_itemname\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_itemAmount\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_price\"></td> \n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_price\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_itemname\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_itemAmount\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_price\"></td> \n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td class=\"sup_price\"></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td></td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<th colspan=\"3\" name=\"\">합계</th>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<td colspan=\"3\" class=\"sup_price\">1400000</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t</table>\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t<div name=\"date\" id=\"date\">\n" +
                "\t\t\t\t\t\t\t\t<div>2024-04-30</div>\n" +
                "\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t</form>");
        approvalDTO.setApprovalDate("2024-04-30 13:11:00");
        approvalDTO.setApprovalStatus("임시저장");
        approvalDTO.setFormNo("sup");


            //파일 처리
            //테스트용 PDF 파일 생성
            MultipartFile pdfFile = null;
            byte[] pdfFileContent = "Test PDF Content".getBytes();
            pdfFile = new MockMultipartFile("testfile.pdf", "testfile.pdf", "application/pdf", pdfFileContent);

            //테스트용 이미지 파일 생성
            MultipartFile imgFile = null;
            byte[] imageFileContent = "Test Image Content".getBytes();
            imgFile = new MockMultipartFile("testimage.jpg", "testimage.jpg", "image/jpeg", imageFileContent);

            files.add(pdfFile);
            files.add(imgFile);


            List<AttachmentDTO> attachmentList = new ArrayList<>();
            AttachmentDTO attachmentDTO1 = new AttachmentDTO();
            attachmentDTO1.setFileNo("2024-sup00002_f001");
            attachmentDTO1.setFileOriname(pdfFile.getOriginalFilename());
            attachmentDTO1.setFileSavename(pdfFile.getName());
            attachmentDTO1.setFileSavepath("C:/login/upload");
            attachmentDTO1.setApprovalNo("2024-sup00002");

            AttachmentDTO attachmentDTO2 = new AttachmentDTO();
            attachmentDTO2.setFileNo("2024-sup00002_f002");
            attachmentDTO2.setFileOriname(imgFile.getOriginalFilename());
            attachmentDTO2.setFileSavename(imgFile.getName());
            attachmentDTO2.setFileSavepath("C:/login/upload");
            attachmentDTO2.setApprovalNo("2024-sup00002");

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
                () -> approvalService.insertApproval(approvalDTO, files)
        );

    }


    //전자결재 회수 테스트
    @DisplayName("전자결재 회수 테스트")
    @ParameterizedTest
    @CsvSource("2024-abs00001")
    void testUpdateApproval (String approvalNo){
        //회수

        // when
        ApprovalDTO approvalDTO = approvalService.updateApproval(approvalNo);

        // then
        Assertions.assertEquals(approvalDTO.getApprovalStatus(), "회수");
    }

    @DisplayName("전자결재 결재 테스트")
    @ParameterizedTest
    @CsvSource("2024-sup00001_apr002")
    void testUpdateApprover (String approverNo){
        //when
        //결재처리 / 반려
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("approverStatus", "반려");
        statusMap.put("rejectReason","이러한 사유로 반려합니다.");

        ApproverDTO approverDTO = approvalService.updateApprover(approverNo, statusMap);

        //then
        Assertions.assertEquals(approverDTO.getApproverStatus(), "반려");
    }



}
