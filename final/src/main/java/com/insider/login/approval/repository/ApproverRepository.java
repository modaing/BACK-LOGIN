package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Approver;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApproverRepository extends JpaRepository<Approver, String> {

    //결재번호로 결재자목록 조회
    List<Approver> findByApprovalNo(String approvalNo);

    //지금 처리해야하는(대기자) 결재자 조회
    @Query("SELECT a FROM Approver a " +
            "WHERE a.approvalNo = :approvalNo " +
            "AND a.approverStatus = '대기' " +
            "ORDER BY a.approverOrder ASC")
    List <Approver> findStandByApproversOrderAsc(@Param("approvalNo") String approvalNo, Pageable pageable);


    //'처리 중'인 전자결재 중 '대기'상태인 결재자 중 가장 작은 순서의 결재자 목록 조회
    @Query(value = "SELECT a.* FROM approver a " +
                    "JOIN (SELECT d.approval_no, MIN(d.approver_order) AS min_order " +
                        "FROM approver d " +
                        "WHERE d.approver_status = '대기' " +
                        "GROUP BY d.approval_no) b " +
                    "ON a.approval_no = b.approval_no " +
                    "AND a.approver_order = b.min_order " +
                    "WHERE a.approval_no IN " +
                        "(SELECT c.approval_no " +
                        "FROM approval c " +
                        "WHERE c.approval_status = '처리 중') " +
                    "AND EXISTS(SELECT 1 " +
                                "FROM approval e " +
                                "WHERE e.approval_no = a.approval_no " +
                                "AND e.approval_title LIKE %:title%)", nativeQuery = true)
    List<Approver> findStandByApprovalsByTitleNative(@Param("title") String title);

    @Modifying
    @Transactional
    @Query("DELETE FROM Approver a WHERE a.approvalNo = :approvalNo")
    void deleteByApprovalNo(@Param("approvalNo") String approvalNo);
}
