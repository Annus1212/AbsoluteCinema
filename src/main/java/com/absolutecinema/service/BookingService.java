package com.absolutecinema.service;

import com.absolutecinema.entity.Booking;
import com.absolutecinema.entity.Session;
import com.absolutecinema.repository.BookingRepository;
import com.absolutecinema.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    private final BookingRepository bookingRepository;
    private final SessionRepository sessionRepository;

    public BookingService(BookingRepository bookingRepository, SessionRepository sessionRepository) {
        this.bookingRepository = bookingRepository;
        this.sessionRepository = sessionRepository;
    }

    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public void cancelBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<Session> getAvailableSessions(Long movieId) {
        return sessionRepository.findByMovieId(movieId);
    }
}