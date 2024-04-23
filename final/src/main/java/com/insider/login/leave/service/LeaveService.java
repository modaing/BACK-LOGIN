package com.insider.login.leave.service;

import com.insider.login.leave.repository.LeaveAccrualRepository;
import com.insider.login.leave.repository.LeaveRepository;
import com.insider.login.leave.repository.LeaveSubmitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    private LeaveAccrualRepository leaveAccrualRepository;
    private LeaveRepository leaveRepository;
    private LeaveSubmitRepository leaveSubmitRepository;

    public LeaveService(LeaveAccrualRepository leaveAccrualRepository, LeaveRepository leaveRepository, LeaveSubmitRepository leaveSubmitRepository) {
        this.leaveAccrualRepository = leaveAccrualRepository;
        this.leaveRepository = leaveRepository;
        this.leaveSubmitRepository = leaveSubmitRepository;
    }

//    public List<LeaveSubmit> selectLeaveSubmitListByMemberId(String applicantId) {
//    }

//    public List<LeaveSubmit>selectLeaveSubmitListByMemberId(String applicantId) {
////
////        LeaveSubmit leaveSubmit1 = new LeaveSubmit(ApproverId,
////                Date.valueOf("2024-04-10"), Date.valueOf("2024-04-11"), Date.valueOf("2024-04-05"), "연차");
////        LeaveSubmit leaveSubmit2 = new LeaveSubmit(applicantId,
////                Date.valueOf("2024-04-12"), Date.valueOf("2024-04-13"), Date.valueOf("2024-04-07"), "반차");
////
////        List<LeaveSubmit> leaveSubmits = Arrays.asList(leaveSubmit1, leaveSubmit2);
////
////        // 4. Mock 객체가 실제 데이터베이스에 엑세스 하지 않고 가짜 데이터를 반환하도록 함
////        when(leaveSubmitRepository.findByMemberId(applicantId)).thenReturn(leaveSubmits);
////
////        // when
////        // 5. 사용할 메소드 해당 레포지토리에 내용 작성
////        List<LeaveSubmit> result = leaveSubmitRepository.findByMemberId(applicantId);
////
////        // then
////        for (LeaveSubmit submit : result) {
////            System.out.println(submit);
////        }
//
//    }




}
