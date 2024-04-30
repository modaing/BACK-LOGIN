package com.insider.login.leave.controller;


import com.insider.login.leave.service.LeaveService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.web.servlet.function.RequestPredicates.param;


@WebMvcTest(LeaveController.class)
@AutoConfigureMockMvc
public class LeaveControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("나의 휴가 신청 내역 조회")
    public void testSelectLeaveSubmitListByMemberId() throws Exception {
        // given
        int applicantId = 241201001;
        int pageNumber = 1;
        String properties = "leaveSubNo";
        String direction = "DESC";

        //when
        MvcResult result = mockMvc.perform(get("/announces")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("memberid", String.valueOf(applicantId))
                        .param("page", String.valueOf(pageNumber))
                        .param("direction", direction)
                        .param("properties", properties))
                .andReturn();
        //then
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(response.getStatus(), 200);
        System.out.println(response.getContentAsString());
    }
}
