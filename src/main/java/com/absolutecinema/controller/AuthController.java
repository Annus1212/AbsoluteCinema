package com.absolutecinema.controller;

import com.absolutecinema.service.AnalyticsService;
import com.absolutecinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    public AuthController() {

    }

    @GetMapping("/")
    public String Home() {
        return "redirect:/auth/login";
        // return "admin/dashboard";
    }

    @GetMapping("/auth/login")
    public String login() {
        return "auth/signin";
    }

    @GetMapping("/auth/register")
    public String register() {
        return "auth/signup";
    }

    @GetMapping("/auth/forgot-password")
    public String forgotPassword() {
        return "auth/forgot-password";
    }

}
