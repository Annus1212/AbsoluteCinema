package com.absolutecinema.controller;

import com.absolutecinema.service.AnalyticsService;
import com.absolutecinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private final MovieService movieService;
    private final AnalyticsService analyticsService;

    public AdminController(MovieService movieService, AnalyticsService analyticsService) {
        this.movieService = movieService;
        this.analyticsService = analyticsService;
    }

    @GetMapping("/admin/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        model.addAttribute("movieCount", movieService.getAllMovies().size());
        model.addAllAttributes(analyticsService.getAnalyticsData());
        return "admin/dashboard";
    }
}