package com.insider.login.insite.repository;

import com.insider.login.insite.entity.InsiteMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  InsiteRepository extends JpaRepository<InsiteMember, Integer> {

    @Query("SELECT m.department.departName, COUNT(m.memberId) FROM InsiteMember m GROUP BY m.department.departName")
    List<Object[]> selectDepartmentMemberCounts();

    @Query(value = "SELECT COUNT(DISTINCT m.member_id) AS totalMembers, " +
            "COUNT(l.LEAVE_SUB_START_DATE) AS leaveCount " +
            "FROM member_info m " +
            "LEFT JOIN LEAVE_SUBMIT l ON m.member_id = l.MEMBER_ID " +
            "AND CURDATE() BETWEEN l.LEAVE_SUB_START_DATE AND l.LEAVE_SUB_END_DATE " +
            "AND l.LEAVE_SUB_STATUS = '승인'",
            nativeQuery = true)
    List<Object[]> selectLeaveMemberCounts();

    @Query(value = "SELECT COUNT(DISTINCT c.member_Id) AS today_commute_count, "
            + "(SELECT COUNT(*) FROM member_info) AS total_member_count "
            + "FROM Commute c "
            + "INNER JOIN member_info m ON c.member_Id = m.member_Id "
            + "WHERE c.working_date = CURRENT_DATE", nativeQuery = true)
    List<Object[]> selectCommuteMemberCounts();

    @Query("SELECT a.memberId, COUNT(a) AS inProgressCount FROM InsiteApproval a WHERE a.approvalStatus = '처리 중' GROUP BY a.memberId")
    List<Object[]> selectApprovalCounts();

    @Query("SELECT COUNT(a) FROM InsiteApprover a WHERE a.approverStatus = '대기'")
    List<Object[]> selectApproverCounts();

    @Query("SELECT ls.leaveMember.memberId, " +
            "SUM(DISTINCT l.leaveDays) AS totalLeaveDays, " +
            "SUM(DISTINCT DATEDIFF(ls.leaveSubEndDate, ls.leaveSubStartDate) + 1) AS consumedDays " +
            "FROM InsiteLeaveSubmit ls " +
            "JOIN insiteLeaves l ON ls.leaveMember.memberId = l.memberId " +
            "WHERE ls.leaveSubStatus = '승인' " +
            "GROUP BY ls.leaveMember.memberId")
    List<Object[]> findAllLeaveInfoCounts();


}
