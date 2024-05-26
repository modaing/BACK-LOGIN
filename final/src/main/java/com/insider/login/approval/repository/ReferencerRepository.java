package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Approval;
import com.insider.login.approval.entity.Referencer;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferencerRepository extends JpaRepository<Referencer, String> {

    List<Referencer> findByApprovalNo(String approvalNo);

    List<Referencer> findByMemberId(int memberId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Referencer r WHERE r.approvalNo = :approvalNo")
    void deleteByApprovalNo(@Param("approvalNo")String approvalNo);

    @Query("SELECT a FROM Approval a JOIN Referencer r ON r.approvalNo = a.approvalNo WHERE r.memberId = :memberId AND a.approvalTitle LIKE %:title%")
    Page<Approval> findApprovalsByMemberIdAndTitle(@Param("memberId")int memberId, @Param("title")String title, Pageable pageable);
}
