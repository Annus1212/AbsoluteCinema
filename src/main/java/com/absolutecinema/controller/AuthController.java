package com.absolutecinema.controller;

import com.absolutecinema.entity.User;
import com.absolutecinema.repository.UserRepository;
import com.absolutecinema.service.AnalyticsService;
import com.absolutecinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/auth/login"; // Redirect to movies page
    }

    @GetMapping("/auth/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // If user is already authenticated, redirect to appropriate dashboard
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            User user = userRepository.findByUsername(authentication.getName());
            if (user != null) {
                if ("admin".equalsIgnoreCase(user.getAccountType())) {
                    return "redirect:/admin/dashboard";
                } else if ("employee".equalsIgnoreCase(user.getAccountType())) {
                    return "redirect:/employee/dashboard";
                } else {
                    return "redirect:/user/dashboard";
                }
            }
        }

        // If not authenticated, show login page
        return "auth/signin";
    }

    @GetMapping("/auth/register")
    public String register() {
        return "auth/signup";
    }

    @PostMapping("/auth/register")
    @Transactional
    public String register(
            @RequestParam(name = "username") String username,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName,
            Model model) {
        try {
            // Check if the username already exists
            if (userRepository.findByUsername(username) != null) {
                model.addAttribute("error", "Username already exists");
                return "auth/signup";
            }

            // Find the next available ID (starting from 13)
            // Long nextId = userRepository.findMaxId().orElse(12L) + 1;

            User user = new User();
            // user.setId(nextId); // Set the ID manually
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            if (username == "admin") {
                user.setAccountType("admin"); // Set account type to admin
            } else if (username == "employee") {
                user.setAccountType("employee"); // Set account type to employee
            } else {
                user.setAccountType("user"); // Default to user account type
            }

            // Save and flush to ensure immediate persistence
            userRepository.saveAndFlush(user);

            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "auth/signup";
        }
    }

    @GetMapping("/auth/forgot-password")
    public String forgotPassword() {
        return "auth/forgot-password";
    }

}
