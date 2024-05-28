package com.insider.login.insite.repository;

import com.insider.login.insite.entity.InsiteMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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


//    List<Object[]> findAllLeaveInfoCounts();

    @Query(value = "SELECT COUNT(DISTINCT m.member_id) AS totalMembers, " +
            "COUNT(DISTINCT CASE WHEN CURDATE() BETWEEN l.LEAVE_SUB_START_DATE AND l.LEAVE_SUB_END_DATE AND l.LEAVE_SUB_STATUS = '승인' THEN m.member_id END) AS leaveCount, " +
            "COUNT(DISTINCT CASE WHEN c.working_date = CURDATE() THEN c.member_id END) AS todayCommuteCount, " +
            "(SELECT COUNT(*) FROM member_info) AS totalMemberCount " +
            "FROM member_info m " +
            "LEFT JOIN LEAVE_SUBMIT l ON m.member_id = l.MEMBER_ID " +
            "LEFT JOIN Commute c ON m.member_id = c.member_Id",
            nativeQuery = true)
    List<Object[]> leavesCommuteMemberCounts();

    @Query(value = "SELECT " +
            "   all_months.month AS month, " +
            "   IFNULL(month_data.birthdayCount, 0) AS birthdayCount, " +
            "   week_data.name AS name, " +
            "   week_data.birthday AS birthday " +
            "FROM " +
            "   (SELECT 1 AS month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 " +
            "    UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 UNION SELECT 11 UNION SELECT 12) AS all_months " +
            "LEFT JOIN " +
            "   (SELECT " +
            "        COUNT(*) AS birthdayCount, " +
            "        MONTH(birthday) AS month " +
            "    FROM member_info " +
            "    WHERE birthday IS NOT NULL " +
            "    GROUP BY MONTH(birthday)) AS month_data " +
            "ON all_months.month = month_data.month " +
            "LEFT JOIN " +
            "   (SELECT " +
            "        name, " +
            "        birthday, " +
            "        MONTH(birthday) AS month " +
            "    FROM member_info " +
            "    WHERE birthday IS NOT NULL AND " +
            "          WEEKOFYEAR(CONCAT(YEAR(CURDATE()), '-', MONTH(birthday), '-', DAY(birthday))) = WEEKOFYEAR(CURDATE())) AS week_data " +
            "ON all_months.month = week_data.month " +
            "ORDER BY all_months.month", nativeQuery = true)
    List<Object[]> birthdayCounts();
}
