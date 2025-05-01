package com.absolutecinema.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.absolutecinema.entity.User;
import com.absolutecinema.repository.UserRepository;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public LoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new ServletException("User not found in database");
        }

        String redirectURL = request.getContextPath();

        // Check account type and redirect accordingly
        if ("admin".equalsIgnoreCase(user.getAccountType())) {
            redirectURL += "/admin/dashboard";
        } else if ("employee".equalsIgnoreCase(user.getAccountType())) {
            redirectURL += "/employee/dashboard";
        } else {
            // Default to user dashboard for any other account type
            redirectURL += "/user/dashboard";
        }

        response.sendRedirect(redirectURL);
    }
}