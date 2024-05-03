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

        //결재번호 : 현재 날짜(연도) - form번호 0000순번(해당 연도-form의 순번)
        //결재자번호 : 결재번호_apr00순번
        //참조자번호 : 결재번호_ref00순번
        //첨부파일번호: 결재번호_f00순번
        ApproverDTO approverDTO1 = new ApproverDTO("2024-con00003_apr001", "2024-con00003", 1, "대기", null, 240401004);
        ApproverDTO approverDTO2 = new ApproverDTO("2024-con00003_apr002", "2024-con00003", 2, "대기", null, 2024001003);

        ReferencerDTO referencerDTO1 = new ReferencerDTO("2024-con00003_ref001", "2024-con00003", 2024001001, 1);
        ReferencerDTO referencerDTO2 = new ReferencerDTO("2024-con00003_ref002", "2024-con00003", 241811, 2);

        approverList.add(approverDTO1);
        approverList.add(approverDTO2);

        referencerList.add(referencerDTO1);
        referencerList.add(referencerDTO2);

        approvalDTO.setApprovalNo("2024-con00003");
        approvalDTO.setMemberId(2024001002);
        approvalDTO.setApprovalTitle("경조금 지급 신청합니다.");
        approvalDTO.setApprovalContent("<form name=\"form\">\n" +
                "\t\t\t\t\t\t\t\t\t<div name=\"wholeForm\"id=\"wholeForm\">\n" +
                "\t\t\t\t\t\t\t\t\t\t<div name=\"titleform\" id=\"titleform\">\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<input type=\"text\" name=\"title\" id=\"title\" placeholder=\"제목\">\n" +
                "\t\t\t\t\t\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t\t\t\t\t\t<table>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr >\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<th>경조사항</th>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td>조부 장례식</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr >\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<th>본인과의 관계</th>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td>조부</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr >\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<th>발생일자</th>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td>2024-04-28</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<th>장소</th>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td>OO장례식장</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t<tr>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<th>휴가기간</th>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t<td>2024-04-28 ~ 2024-05-01</td>\n" +
                "\t\t\t\t\t\t\t\t\t\t\t</tr>\n" +
                "\t\t\t\t\t\t\t\t\t   </table>\n" +
                "\t\t\t\t\t\t\t\t\t </div>\n" +
                "\t\t\t\t\t\t\t\t  <div name=\"date\" id=\"date\">\n" +
                "\t\t\t\t\t\t\t\t\t<div>2024-05-03</div>\n" +
                "\t\t\t\t\t\t\t\t  </div>\n" +
                "\t\t\t\t\t\t\t\t</form>");
        approvalDTO.setApprovalDate("2024-05-03 10:02:02");
        approvalDTO.setApprovalStatus("처리 중");
        approvalDTO.setFormNo("con");


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
            attachmentDTO1.setFileNo("2024-con00003_f001");
            attachmentDTO1.setFileOriname(pdfFile.getOriginalFilename());
            attachmentDTO1.setFileSavename(pdfFile.getName());
            attachmentDTO1.setFileSavepath("C:/login/upload");
            attachmentDTO1.setApprovalNo("2024-con00003");

            AttachmentDTO attachmentDTO2 = new AttachmentDTO();
            attachmentDTO2.setFileNo("2024-con00003_f002");
            attachmentDTO2.setFileOriname(imgFile.getOriginalFilename());
            attachmentDTO2.setFileSavename(imgFile.getName());
            attachmentDTO2.setFileSavepath("C:/login/upload");
            attachmentDTO2.setApprovalNo("2024-con00003");

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

    //전자결재 상세조회 테스트
    @DisplayName("전자결재 상세조회 테스트")
    @ParameterizedTest
    @CsvSource("2024-con00001")
    void testSelectApprovalList(String approvalNo){
        //given

        //when
        ApprovalDTO approvalDTO = approvalService.selectApproval(approvalNo);

        //then
        Assertions.assertNotNull(approvalDTO);
        System.out.println(approvalDTO);
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
    @CsvSource("2024-con00003_apr002")
    void testUpdateApprover (String approverNo){
        //when
        //결재처리 / 반려
        Map<String, String> statusMap = new HashMap<>();
        statusMap.put("approverStatus", "승인");
        statusMap.put("rejectReason","이러한 사유로 반려합니다.");

        ApproverDTO approverDTO = approvalService.updateApprover(approverNo, statusMap);
        System.out.println("***** TEST : approver : " + approverDTO );

        System.out.println("***** approverDTO.getApproverStatus(): "  + approverDTO.getApproverStatus());

        //then
        Assertions.assertEquals(approverDTO.getApproverStatus(), statusMap.get("approverStatus"));
    }


    @DisplayName("전자결재 목록 조회 테스트")
    @Test
    void testSelectApprovalList(){
        //given
        int memberId = 2024001002;

        Map<String, Object> condition = new HashMap<>();
        condition.put("flag", "received");
        condition.put("offset", 10);
        condition.put("limit", 10);

        //when
        List<ApprovalDTO> approvalList = approvalService.selectApprovalList(memberId, condition);

        System.out.println("*****TEST : 목록 size() : " + approvalList.size());

        //then
        Assertions.assertNotNull(approvalList);
//        approvalList.forEach(System.out::println);
    }


}
