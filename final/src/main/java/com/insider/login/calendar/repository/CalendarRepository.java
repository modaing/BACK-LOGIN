package com.insider.login.calendar.repository;

import com.insider.login.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
    @Query("SELECT c FROM Calendar c " +
            "WHERE YEAR(c.calendarStart) = :year " +
            "AND MONTH(c.calendarStart) = :month")
    List<Calendar> findByMonth(@Param("year") int year, @Param("month") int month);

    @Query("SELECT c FROM Calendar c " +
            "WHERE YEAR(c.calendarStart) = :year " +
            "AND MONTH(c.calendarStart) = :month " +
            "AND WEEK(c.calendarStart) = :week")
    List<Calendar> findByWeek(@Param("year") int year, @Param("month") int month, @Param("week") int week);

    @Query("SELECT c FROM Calendar c " +
            "WHERE YEAR(c.calendarStart) = :year " +
            "AND MONTH(c.calendarStart) = :month " +
            "AND DAY(c.calendarStart) = :day")
    List<Calendar> findByDay(@Param("year") int year, @Param("month") int month, @Param("day") int day);
}
