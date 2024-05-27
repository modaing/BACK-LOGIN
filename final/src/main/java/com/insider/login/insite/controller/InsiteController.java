package com.insider.login.insite.controller;

import com.insider.login.insite.dto.InsiteLeaveInfoDTO;
import com.insider.login.insite.service.InsiteService;
import com.insider.login.leave.dto.LeaveInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/insites")
public class InsiteController {

    private final InsiteService insiteService;

    /** 팀 별 구성원 수 */
    @GetMapping("/departments")
    public List<Object[]> departmentMemberCounts() {
        return insiteService.selectDepartmentMemberCounts();
    }

    /** 휴가자 */
    @GetMapping("/leaves")
    public List<Object[]> leaveMemberCounts() {
        return insiteService.selectLeaveMemberCounts();
    }

    /** 출근 */
    @GetMapping("/commutes")
    public List<Object[]> commuteMemberCounts() {
        return insiteService.selectCommuteMemberCounts();
    }

    /** 승인 대기 전자결재(상신) */
    @GetMapping("/approvals")
    public List<Object[]> approvalMemberCounts() {
        return insiteService.selectApprovalCounts();
    }

    /** 미처리 전자결재 (수신)*/
    @GetMapping("/approvers")
    public List<Object[]> approverMemberCounts() {
        return insiteService.selectApproverCounts();
    }

    /** 휴가 잔여일 */
    @GetMapping("/leaves/{memberId}")
    public List<InsiteLeaveInfoDTO> getLeaveInfo(@PathVariable("memberId") int memberId) {

        return insiteService.getAllLeaveInfo(memberId);
    }

    /** 금일 휴가자 + 출근자 + 전체 구성원 수 */
    @GetMapping("/leaves/commutes")
    public Map<String, Object> leavesCommuteMemberCounts() {
        Map<String, Object> data = new HashMap<>();
        try {
            List<Object[]> insiteData = insiteService.leavesCommuteMemberCounts();
            if (insiteData != null && !insiteData.isEmpty()) {
                Object[] row = insiteData.get(0);
                data.put("totalMembers", row[0]);
                data.put("leaveCount", row[1]);
                data.put("todayCommuteCount", row[2]);
                data.put("totalMemberCount", row[3]);
            }
        } catch (Exception e) {
            data.put("error", e.getMessage());
        }
        return data;
    }

    /** 월별 생일자 수 / 이번주 생일자 / 금일 생일자 */
    @GetMapping("/members")
    public List<Object[]> getBirthdayStats() {
        return insiteService.birthdayCounts();
    }

}
