package com.insider.leave.repository;

import com.insider.leave.entity.LeaveAccrual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveAccrualRepository extends JpaRepository<LeaveAccrual, Integer> {
}
