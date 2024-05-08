package com.insider.login.approval.controller;

import com.insider.login.approval.service.ApprovalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(ApprovalController.class)
public class ApprovalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ApprovalService approvalService;


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
    public void SelectApproval() throws Exception {
        //given
        String approvalNo = "2024-con00001";

        //when
        //then
       mockMvc.perform(MockMvcRequestBuilders
               .get("/approvals/{approvalNo}", approvalNo)
                       .with(user("240501959").password("0000"))
               .contentType(MediaType.APPLICATION_JSON))    //요청의 content type 설정
               .andExpect(MockMvcResultMatchers.status().isOk())    //HTTP 상태코드가 200인지 확인
               .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))  //응답의 content type을 확인
               .andReturn();
    }

    @DisplayName("전자 결재 목록 조회")
    @Test
    public void SelectApprovalList(){
        // /approvals?fg={given}&page={pageno}
        //RequestParam = fg, page
        //memberId : ""
        // selectApprovalList(int memberId, Map<String, Object> condition)
        //  condition.put("flag", "receivedRef");
        //  condition.put("offset", 10);
        //  condition.put("limit", 10);

        //given
        int memberId = 2024001002;

        //when

        //then
    }

}
