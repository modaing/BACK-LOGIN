package com.insider.login.calendar.controller;

import com.insider.login.calendar.service.CalendarService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarContorller {

    private final CalendarService calendarService;

    public CalendarContorller(CalendarService calendarService) {
        this.calendarService = calendarService;
    }
}
