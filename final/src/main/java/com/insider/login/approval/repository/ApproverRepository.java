package com.insider.login.approval.repository;

import com.insider.login.approval.entity.Approver;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ApproverRepository {

    @PersistenceContext
    private EntityManager manager;


    public void save(Approver approver){
        manager.persist(approver);
    }

    public List<Approver> findByApprovalId(String approvalNo) {
        List<Approver> approverList = manager.createQuery("SELECT a FROM Approver a WHERE a.approverNo = :approvalNo", Approver.class)
                .setParameter("approvalNo", approvalNo)
                .getResultList();

        return approverList;
    }
}
