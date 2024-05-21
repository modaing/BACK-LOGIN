package com.insider.login.commute.repository;

import com.insider.login.commute.entity.Correction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CorrectionRepository extends JpaRepository<Correction, Integer> {

    Correction findByCorrNo(int corrNo);

    Page<Correction> findAllByCorrRegistrationDateBetween(LocalDate startDayOfMonth, LocalDate endDayOfMonth, Pageable pageable);

//    Page<Correction> findByCommuteMemberIdAndCorrRegistrationDateBetween(int memberId, LocalDate startDayOfMonth, LocalDate endDayOfMonth, Pageable pageable);

//    @Query("SELECT c, co " +
//            "FROM Correction c " +
//            "LEFT JOIN c.commute co ON (c.commuteNo = co.commuteNo)" +
//            "WHERE co.memberId = :memberId " +
//            "AND c.corrRegistrationDate BETWEEN :startDayOfMonth AND :endDayOfMonth " +
//            "ORDER BY c.corrRegistrationDate DESC")
//    Page<Object[]> findCorrectionAndCommuteByMemberIdAndDates(@Param("memberId") int memberId,
//                                                              @Param("startDayOfMonth") LocalDate startDayOfMonth,
//                                                              @Param("endDayOfMonth") LocalDate endDayOfMonth,
//                                                              Pageable pageable);


}
