package com.insider.login.commute.repository;

import com.insider.login.commute.entity.Correction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CorrectionRepository extends JpaRepository<Correction, Integer> {

    Correction findByCorrNo(int corrNo);

    Page<Correction> findAllByCorrRegistrationDateBetween(LocalDate startDayOfMonth, LocalDate endDayOfMonth, Pageable pageable);

    Correction findByCorrNoAndCorrRegistrationDateBetween(int corrNo, LocalDate startDayOfMonth, LocalDate endDayOfMonth);

//    List<Correction> findByCommuteNo(int commuteNo);


//    Page<Correction> findByCommuteMemberIdAndCorrRegistrationDateBetween(int memberId, LocalDate startDayOfMonth, LocalDate endDayOfMonth, Pageable pageable);

}
