package com.absolutecinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @GetMapping("/user/dashboard")
    public String getUserDashboard() {
        return "user/dashboard";
    }
}

