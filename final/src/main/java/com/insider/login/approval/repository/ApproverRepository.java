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
        List<Approver> approverList = manager.createQuery("SELECT a FROM Approver a WHERE a.approvalNo = :approvalNo", Approver.class)
                .setParameter("approvalNo", approvalNo)
                .getResultList();

        return approverList;
    }

    public void update(Approver approver) {

        manager.merge(approver);
    }

    public Approver findStatusById(String approvalNo) {
        String query = "SELECT approver_no, approval_no, approver_order, approver_status, approver_date, member_id" +
                " FROM approver" +
                " WHERE approval_no = ?" +
                " AND approver_status = '처리 중'" +
                " ORDER BY approver_order ASC" +
                " LIMIT 1";

        List<Approver> approverList = manager.createNativeQuery(query, Approver.class)
                .setParameter(1, approvalNo)
                .getResultList();

        Approver approver = null;

        if(approverList.size() > 0 || !approverList.isEmpty())
        {
            approver = approverList.get(0);
        }

        return approver;
    }

    public List<Approver> findByStandById() {
        String query = "SELECT approver_no, approval_no, approver_order, approver_status, approver_date, member_id" +
                " FROM approver a" +
                " JOIN (" +
                "       SELECT approval_no, MIN(approver_order) AS min_order" +
                "       FROM approver" +
                "       WHERE approver_status = '대기'" +
                "       GROUP BY approval_no" +
                ") b ON a.approval_no = b.approval_no AND a.approver_order = b.min_order" +
                " WHERE a.approval_no IN (" +
                "   SELECT approval_no" +
                "   FROM approval" +
                "   WHERE approval_status = '처리 중'" +
                ")";

        List<Approver> approverList = manager.createNativeQuery(query, Approver.class)
                .getResultList();

        return approverList;
    }
}
