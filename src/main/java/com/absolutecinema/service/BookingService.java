package com.absolutecinema.service;

import com.absolutecinema.entity.Booking;
import com.absolutecinema.entity.User;
import com.absolutecinema.repository.BookingRepository;
import com.absolutecinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MovieRepository movieRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, MovieRepository movieRepository) {
        this.bookingRepository = bookingRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public Booking createBooking(User user, Long movieId, Long sessionId, int numberOfTickets, double totalPrice) {
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setMovieId(movieId);
        booking.setSessionId(sessionId);
        booking.setNumberOfTickets(numberOfTickets);
        booking.setTotalPrice(totalPrice);
        booking.setBookingTime(LocalDateTime.now());
        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public List<Booking> getUserBookings(User user) {
        List<Booking> bookings = bookingRepository.findByUser(user);
        return bookings.stream()
                .map(booking -> {
                    booking.setMovie(movieRepository.findById(booking.getMovieId())
                            .orElse(null));
                    return booking;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    @Transactional
    public void cancelBooking(Long id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
    }
}