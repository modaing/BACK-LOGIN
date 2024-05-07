package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Approval;
import com.insider.login.approval.entity.Form;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApprovalRepository {

    @PersistenceContext
    private EntityManager manager;

    public void insertForm(Form newForm) {
        manager.persist(newForm);
    }

    public void save(Approval approval){
        manager.persist(approval);
    }

    public Approval findById(String approvalNo){
        return manager.find(Approval.class, approvalNo);
    }

    public void update(Approval approval){
        manager.merge(approval);
    }


    public List<Approval> findByMemberId(int memberId) {
        String query = "SELECT approval_no, member_id, approval_title, approval_content, approval_date,  approval_status, reject_reason, form_no" +
                " FROM approval" +
                " WHERE member_id = ?" +
                " AND approval_status != '임시저장'";

        List<Approval> approvalList = manager.createNativeQuery(query, Approval.class)
                .setParameter(1, memberId)
                .getResultList();

        return approvalList;
    }

    public List<Approval> findTempByMemberId(int memberId) {
        String query =  "SELECT approval_no, member_id, approval_title, approval_content, approval_date,  approval_status, reject_reason, form_no" +
                " FROM approval" +
                " WHERE member_id = ?" +
                " AND approval_status = '임시저장'";

        List<Approval> approvalList = manager.createNativeQuery(query, Approval.class)
                .setParameter(1, memberId)
                .getResultList();

        return approvalList;
    }

    public void deleteById(String approvalNo) {
        Approval approval = findById(approvalNo);
        Approval managedApproval = manager.merge(approval);
        manager.remove(managedApproval);
    }
}

