package com.insider.login.leave.service;

import com.insider.login.leave.dto.LeaveSubmitDTO;
import com.insider.login.leave.entity.LeaveSubmit;
import com.insider.login.leave.service.LeaveService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;

@SpringBootTest
public class LeaveServiceTests {

    @Autowired
    private LeaveService leaveService;



    // 3. 테스트 코드 작성
    @Test
    @DisplayName("휴가 신청 내역 조회")
    void testSelectLeaveSubmitListByMemberId() {
        // given
//        String applicantId = "2023123-XXXXX";
//
//        // when
//        List<LeaveSubmit> leaveSubmits = leaveService.selectLeaveSubmitListByMemberId(applicantId);
//
//        //then
//        Assertions.assertNotNull(leaveSubmits);
//        leaveSubmits.forEach(System.out::println);
    }

    @Test
    @DisplayName(("휴가 신청"))
    void testInsertSubmit() {
        // given
        LeaveSubmitDTO leaveSubmitDTO = new LeaveSubmitDTO("2023123-XXXXX", Date.valueOf("2024-04-10"), Date.valueOf("2024-04-11"), Date.valueOf("2024-04-05"), "연차");

        // when


        //then


    }
}
