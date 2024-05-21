package com.insider.login.insite.controller;

import com.insider.login.insite.service.InsiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/insites")
public class InsiteController {

    private final InsiteService insiteService;

    @GetMapping("/departments")
    public List<Object[]> departmentMemberCounts() {
        return insiteService.selectDepartmentMemberCounts();
    }

    @GetMapping("/leaves")
    public List<Object[]> leaveMemberCounts() {
        return insiteService.selectLeaveMemberCounts();
    }

    @GetMapping("/commutes")
    public List<Object[]> commuteMemberCounts() {
        return insiteService.selectCommuteMemberCounts();
    }

}
