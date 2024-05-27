package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Attachment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, String> {


    public List<Attachment> findByApprovalNo(String approvalNo);

    @Modifying
    @Transactional
    @Query("DELETE FROM Apr_attachment a WHERE a.approvalNo = :approvalNo")
    public void deleteByApprovalNo(@Param("approvalNo") String approvalNo);
}
