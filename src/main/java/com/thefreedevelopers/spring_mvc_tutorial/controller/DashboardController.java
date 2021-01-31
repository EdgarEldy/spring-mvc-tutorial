package com.thefreedevelopers.spring_mvc_tutorial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

    @RequestMapping("/")
    public String IndexPage(){
        return "dashboard/index";
    }
}
