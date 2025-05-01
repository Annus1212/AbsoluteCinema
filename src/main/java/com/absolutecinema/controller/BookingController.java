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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public String createBooking(@RequestParam Long sessionId,
            @RequestParam Long movieId,
            @RequestParam int numberOfTickets,
            @RequestParam double totalPrice) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        bookingService.createBooking(user, movieId, sessionId, numberOfTickets, totalPrice);
        return "redirect:/bookings/my-bookings";
    }

    @GetMapping("/my-bookings")
    public String getMyBookings(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        List<Booking> bookings = bookingService.getUserBookings(user);
        model.addAttribute("bookings", bookings);
        return "user/my-bookings";
    }

    @GetMapping("/history")
    public String getBookingHistory(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        List<Booking> bookings = bookingService.getUserBookings(user);
        model.addAttribute("bookings", bookings);
        return "bookings/history";
    }

    @GetMapping("/{id}")
    public String getBookingDetails(@PathVariable Long id, Model model) {
        Booking booking = bookingService.getBookingById(id);
        model.addAttribute("booking", booking);
        return "user/booking-details";
    }

    @PostMapping("/{id}/cancel")
    @ResponseBody
    public String cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return "Booking cancelled successfully";
    }
}

class BookingRequest {
    private Long sessionId;
    private int numberOfTickets;

    // Getters and Setters
    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}