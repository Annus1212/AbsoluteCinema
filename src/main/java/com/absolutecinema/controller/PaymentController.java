package com.absolutecinema.controller;

import com.absolutecinema.entity.User;
import com.absolutecinema.service.PaymentService;
import com.absolutecinema.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user/payment")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;
    private final UserService userService;

    @Value("${stripe.public.key}")
    private String stripePublicKey;

    @Autowired
    public PaymentController(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @GetMapping
    public String getPaymentPage(Model model) {
        model.addAttribute("stripePublicKey", stripePublicKey);
        return "user/payment";
    }

    @PostMapping("/process")
    @ResponseBody
    public Map<String, Object> processPayment(@RequestBody Map<String, Object> paymentRequest) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Get authenticated user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                throw new RuntimeException("User not authenticated");
            }

            User user = userService.findByUsername(auth.getName());
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            // Validate required fields
            if (!paymentRequest.containsKey("paymentMethodId") ||
                    !paymentRequest.containsKey("amount") ||
                    !paymentRequest.containsKey("movieId") ||
                    !paymentRequest.containsKey("sessionId")) {
                throw new IllegalArgumentException("Missing required payment information");
            }

            // Extract and validate payment details
            String paymentMethodId = (String) paymentRequest.get("paymentMethodId");

            // Validate amount
            Object amountObj = paymentRequest.get("amount");
            if (amountObj == null || amountObj.toString().trim().isEmpty()) {
                throw new IllegalArgumentException("Amount cannot be empty");
            }
            Double amount;
            try {
                amount = Double.parseDouble(amountObj.toString());
                if (amount <= 0) {
                    throw new IllegalArgumentException("Amount must be greater than 0");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid amount format");
            }

            // Validate movieId
            Object movieIdObj = paymentRequest.get("movieId");
            if (movieIdObj == null || movieIdObj.toString().trim().isEmpty()) {
                throw new IllegalArgumentException("Movie ID cannot be empty");
            }
            Long movieId;
            try {
                movieId = Long.parseLong(movieIdObj.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid movie ID format");
            }

            // Validate sessionId
            Object sessionIdObj = paymentRequest.get("sessionId");
            if (sessionIdObj == null || sessionIdObj.toString().trim().isEmpty()) {
                throw new IllegalArgumentException("Session ID cannot be empty");
            }
            Long sessionId;
            try {
                sessionId = Long.parseLong(sessionIdObj.toString());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid session ID format");
            }

            // Get seats and snacks
            List<Object> seats = (List<Object>) paymentRequest.get("seats");
            Map<String, Object> snacks = (Map<String, Object>) paymentRequest.get("snacks");

            logger.info("Processing payment request - User: {}, Amount: {}, Movie: {}, Session: {}",
                    user.getUsername(), amount, movieId, sessionId);

            // Process payment
            paymentService.processPayment(paymentMethodId, amount, seats, snacks, user, movieId, sessionId);

            response.put("success", true);
            response.put("redirectUrl", "/user/dashboard");

            logger.info("Payment processed successfully for user: {}", user.getUsername());

        } catch (IllegalArgumentException e) {
            logger.error("Invalid payment request: {}", e.getMessage());
            response.put("success", false);
            response.put("error", e.getMessage());
        } catch (Exception e) {
            logger.error("Payment processing error: {}", e.getMessage());
            response.put("success", false);
            response.put("error", e.getMessage());
        }

        return response;
    }

    @GetMapping("/success")
    public String getSuccessPage() {
        return "user/payment-success";
    }
}