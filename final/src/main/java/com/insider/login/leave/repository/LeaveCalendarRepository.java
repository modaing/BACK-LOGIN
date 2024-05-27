package com.insider.login.leave.repository;

import com.insider.login.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveCalendarRepository extends JpaRepository<Calendar, Integer> {
}
