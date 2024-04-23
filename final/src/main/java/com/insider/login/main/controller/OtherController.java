package com.insider.login.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/other")
public class OtherController {

    @GetMapping("/insite")
    public String insite() {

        return "other/insite";
    }

    @GetMapping("/list-notice")
    public String notice() {

        return "other/list-notice";
    }
}
