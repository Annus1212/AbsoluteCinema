package com.absolutecinema.controller;

import com.absolutecinema.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookings")
public class BookingHistoryController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/history")
    public String showBookingHistory(Model model) {
        model.addAttribute("bookings", bookingService.getUserBookings());
        return "bookings/history";
    }
}