package com.insider.login.approval.controller;

import com.insider.login.approval.entity.Approval;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/approvals")
public class ApprovalController {

    public ModelAndView insertApproval (@PathVariable Approval approval, ModelAndView mv){


        return mv;
    }
}
