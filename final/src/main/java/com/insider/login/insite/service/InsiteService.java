package com.insider.login.insite.service;

import com.insider.login.insite.dto.InsiteLeaveInfoDTO;
import com.insider.login.insite.repository.InsiteRepository;
import com.insider.login.leave.dto.LeaveInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

//    public List<Object[]> selectLeaveInfoCounts(int memberId) {
//
//
//        return insiteRepository.findLeaveInfoCounts(memberId);
//    }

    public List<InsiteLeaveInfoDTO> getAllLeaveInfo(int memberId) {
        List<Object[]> results = insiteRepository.findAllLeaveInfoCounts();
        List<InsiteLeaveInfoDTO> leaveInfoList = new ArrayList<>();

        for (Object[] result : results) {

            int consumedDays = ((Number) result[0]).intValue();
            memberId = ((Number) result[1]).intValue();
            int totalLeaveDays = ((Number) result[2]).intValue();

            InsiteLeaveInfoDTO leaveInfo = new InsiteLeaveInfoDTO(consumedDays, memberId, totalLeaveDays);
            leaveInfoList.add(leaveInfo);
        }

        return leaveInfoList;
    }
}
