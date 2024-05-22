package com.insider.login.commute.repository;

import com.insider.login.commute.entity.Correction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public class CorrectionAndCommuteRepository {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public Page<Object[]> selectRequestForCorrectListByMemberId(int memberId, LocalDate startDayOfMonth, LocalDate endDayOfMonth, Pageable pageable) {
        String jpql = "SELECT c, co " +
                "FROM Commute co " +
                "JOIN Correction c ON c.commuteNo = co.commuteNo " +
                "WHERE co.memberId = :memberId " +
                "AND c.corrRegistrationDate BETWEEN :startDayOfMonth AND :endDayOfMonth " +
                "ORDER BY c.corrRegistrationDate DESC";

        Query query = manager.createQuery(jpql);
        query.setParameter("memberId", memberId);
        query.setParameter("startDayOfMonth", startDayOfMonth);
        query.setParameter("endDayOfMonth", endDayOfMonth);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> results = query.getResultList();

        long totalCount = (long) manager.createQuery("SELECT COUNT(c) " +
                        "FROM Commute co " +
                        "JOIN Correction c ON c.commuteNo = co.commuteNo " +
                        "WHERE co.memberId = :memberId " +
                        "AND c.corrRegistrationDate BETWEEN :startDayOfMonth AND :endDayOfMonth", Long.class)
                .setParameter("memberId", memberId)
                .setParameter("startDayOfMonth", startDayOfMonth)
                .setParameter("endDayOfMonth", endDayOfMonth)
                .getSingleResult();

        return new PageImpl<>(results, pageable, totalCount);
    }

    public Page<Object[]> selectRequestForCorrectList(LocalDate startDayOfMonth, LocalDate endDayOfMonth, Pageable pageable) {
        String jpql = "SELECT co, c, m, d " +
                "FROM Commute co " +
                "LEFT JOIN Correction c ON c.commuteNo = co.commuteNo " +
                "LEFT JOIN CommuteMember m ON m.memberId = co.memberId " +
                "LEFT JOIN CommuteDepartment d ON d.departNo = m.departNo " +
                "WHERE c.corrRegistrationDate BETWEEN :startDayOfMonth AND :endDayOfMonth " +
                "ORDER BY c.corrRegistrationDate DESC";

        Query query = manager.createQuery(jpql);
        query.setParameter("startDayOfMonth", startDayOfMonth);
        query.setParameter("endDayOfMonth", endDayOfMonth);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> results = query.getResultList();

        long totalCount = (long) manager.createQuery("SELECT COUNT(c) " +
                "FROM Commute co " +
                "LEFT JOIN Correction c ON c.commuteNo = co.commuteNo " +
                "LEFT JOIN CommuteMember m ON m.memberId = co.memberId " +
                "LEFT JOIN CommuteDepartment d ON d.departNo = m.departNo " +
                "WHERE c.corrRegistrationDate BETWEEN :startDayOfMonth AND :endDayOfMonth", Long.class)
                .setParameter("startDayOfMonth", startDayOfMonth)
                .setParameter("endDayOfMonth", endDayOfMonth)
                .getSingleResult();

        return new PageImpl<>(results, pageable, totalCount);
    }
}
