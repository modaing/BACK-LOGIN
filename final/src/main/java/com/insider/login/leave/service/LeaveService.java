package com.insider.login.leave.service;

import com.insider.login.leave.entity.LeaveSubmit;
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

    public List<LeaveSubmit> selectLeaveSubmitListByMemberId(String applicantId) {
        return leaveSubmitRepository.findByMemberId(applicantId);

    }
}
