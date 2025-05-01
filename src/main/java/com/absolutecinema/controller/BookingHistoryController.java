package com.absolutecinema.controller;

import com.absolutecinema.entity.Booking;
import com.absolutecinema.entity.User;
import com.absolutecinema.service.BookingService;
import com.absolutecinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/booking-history")
public class BookingHistoryController {

    private final BookingService bookingService;
    private final UserService userService;

    @Autowired
    public BookingHistoryController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @GetMapping
    public String getUserBookings(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        List<Booking> bookings = bookingService.getUserBookings(user);
        model.addAttribute("bookings", bookings);
        return "bookings/history";
    }
}