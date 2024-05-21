package com.insider.login.commute.repository;

import com.insider.login.commute.entity.Correction;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class CorrectionAndCommuteRepository {

    @PersistenceContext
    private EntityManager manager;

    public Page<Object[]> selectRequestForCorrectListByMemberId(int memberId, LocalDate startDayOfMonth, LocalDate endDayOfMonth, Pageable pageable) {
        String jpql = "SELECT c, co " +
                        "FROM Correction c " +
                        "LEFT JOIN FETCH Commute co ON (c.commuteNo = co.commuteNo)" +
                        "WHERE co.memberId = :memberId " +
                        "AND c.corrRegistrationDate BETWEEN :startDayOfMonth AND :endDayOfMonth " +
                        "ORDER BY c.corrRegistrationDate DESC";

        Query query = manager.createQuery(jpql, Object[].class);
        query.setParameter("memberId", memberId);
        query.setParameter("startDayOfMonth", startDayOfMonth);
        query.setParameter("endDayOfMonth", endDayOfMonth);

        /** 페이징 처리 */
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Object[]> results = query.getResultList();
        long totalCount = (long) manager.createQuery("SELECT COUNT(c) " +
                                                        "FROM Correction c " +
                                                        "LEFT JOIN Commute co ON c.commuteNo = co.commuteNo " +
                                                        "WHERE co.memberId = :memberId " +
                                                        "AND c.corrRegistrationDate BETWEEN :startDayOfMonth AND :endDayOfMonth", Long.class)
                                                        .setParameter("memberId", memberId)
                                                        .setParameter("startDayOfMonth", startDayOfMonth)
                                                        .setParameter("endDayOfMonth", endDayOfMonth)
                                                        .getSingleResult();

//        int totalPages = (int) Math.ceil((double) totalCount / pageable.getPageSize());
//        int currentPage = pageable.getPageNumber();

        return new PageImpl<>(results, pageable, totalCount);
    }
}
