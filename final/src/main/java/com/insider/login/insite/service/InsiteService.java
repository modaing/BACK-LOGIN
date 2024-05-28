package com.insider.login.insite.service;

import com.insider.login.insite.dto.InsiteLeaveInfoDTO;
import com.insider.login.insite.repository.InsiteRepository;
import com.insider.login.leave.dto.LeaveInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InsiteService {

    private final InsiteRepository insiteRepository;

    public List<Object[]> selectDepartmentMemberCounts() {

        return insiteRepository.selectDepartmentMemberCounts();
    }

    public List<Object[]> selectLeaveMemberCounts() {

        return insiteRepository.selectLeaveMemberCounts();
    }

    public List<Object[]> selectCommuteMemberCounts() {

        return insiteRepository.selectCommuteMemberCounts();
    }

    public List<Object[]> selectApprovalCounts() {

        return insiteRepository.selectApprovalCounts();
    }

    public List<Object[]> selectApproverCounts() {

        return insiteRepository.selectApproverCounts();
    }


    public List<Object[]> leavesCommuteMemberCounts() {
        return insiteRepository.leavesCommuteMemberCounts();

    }

    public List<Object[]> birthdayCounts() {

        return insiteRepository.birthdayCounts();
    }
}
