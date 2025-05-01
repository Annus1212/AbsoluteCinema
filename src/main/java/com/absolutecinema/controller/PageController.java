package com.absolutecinema.controller;

import com.absolutecinema.entity.User;
import com.absolutecinema.service.LoyaltyService;
import com.absolutecinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final LoyaltyService loyaltyService;
    private final UserService userService;

    @Autowired
    public PageController(LoyaltyService loyaltyService, UserService userService) {
        this.loyaltyService = loyaltyService;
        this.userService = userService;
    }

    @GetMapping("/feedback")
    public String getFeedbackPage() {
        return "feedback/feedback";
    }

    @GetMapping("/contact")
    public String getContactPage() {
        return "contact/contact";
    }

    @GetMapping("/loyalty")
    public String getLoyaltyPage(Model model) {
        // Get the currently logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        // Fetch points directly from the User entity
        int points = user.getPoints();

        // Optionally, you can keep loyaltyLevel logic if you want to derive it from
        // points
        String loyaltyLevel = "BRONZE";
        if (points >= 1000) {
            loyaltyLevel = "GOLD";
        } else if (points >= 500) {
            loyaltyLevel = "SILVER";
        }

        // Add user and points to the model
        model.addAttribute("user", user);
        model.addAttribute("points", points);
        model.addAttribute("loyaltyLevel", loyaltyLevel);

        return "user/loyaltyuser";
    }
}