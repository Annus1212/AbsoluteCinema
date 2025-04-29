package com.absolutecinema.controller;

import com.absolutecinema.entity.User;
import com.absolutecinema.repository.UserRepository;
import com.absolutecinema.service.AnalyticsService;
import com.absolutecinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public AuthController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;

    }

    // @GetMapping("/")
    // public String Home() {
    //     return "redirect:/auth/login";
    //     // return "admin/dashboard";
    // }

    @GetMapping("/auth/login")
    public String login() {
        return "auth/signin";
    }

    // @PostMapping("/auth/login")
    // public String login(@RequestParam String username, @RequestParam String password, Model model) {
    //     return "redirect:/admin/dashboard";
    // }

    @GetMapping("/auth/register")
    public String register() {
        return "auth/signup";
    }

    @PostMapping("/auth/register")
    public String register(
        @RequestParam String username, 
        @RequestParam String password,
        @RequestParam String email, 
        Model model
    ) {
        // Here you would typically save the user to the database and handle registration logic
        // For now, we'll just redirect to the login page

        // Check if the username already exists
        if (userRepository.findByUsername(username) != null) {
            model.addAttribute("error", "Username already exists");
            return "auth/signup"; // Return to the registration page with an error message
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // In a real application, you should hash the password before saving it
        user.setEmail(email);
        userRepository.save(user); // Save the user to the database

        return "redirect:/auth/login";
    }

    @GetMapping("/auth/forgot-password")
    public String forgotPassword() {
        return "auth/forgot-password";
    }

}
