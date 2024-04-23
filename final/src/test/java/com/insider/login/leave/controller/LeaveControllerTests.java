//package com.insider.leave.controller;
//
//import com.insider.leave.controller.LeaveController;
//import com.insider.leave.entity.LeaveSubmit;
//import com.insider.leave.service.LeaveService;
//import jakarta.persistence.Column;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDate;
//
//import static org.mockito.Mockito.when;
//
//@WebMvcTest(LeaveController.class)
//@AutoConfigureMockMvc
//public class LeaveControllerTests {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private LeaveService leaveService;
//
//    public void testSelectLeaveSubmitListByMemberId() throws Exception {
//        // given
//        String requestBody = "{"
//                + "\"memberId\": \"20240410-12312\","
//                + "\"leaveSubStartDate\": \"2024-04-29\","
//                + "\"leaveSubEndDate\": \"2024-04-30\","
//                + "\"leaveSubApplyDate\": \"2024-04-21\","
//                + "\"leaveSubType\": \"연차\""
//                + "}";
//
////        when(leaveService.selectLeaveSubmitListByMemberId()).thenReturn(/*반환받을 값*/);
//
//        //when
//        //then
//
//    }
//}
