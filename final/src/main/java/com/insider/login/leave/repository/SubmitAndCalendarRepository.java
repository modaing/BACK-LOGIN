package com.insider.login.leave.repository;

import com.insider.login.leave.entity.SubmitAndCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmitAndCalendarRepository extends JpaRepository<SubmitAndCalendar, Integer> {

}
