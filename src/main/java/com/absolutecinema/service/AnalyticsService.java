package com.absolutecinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.absolutecinema.repository.BookingRepository;
import com.absolutecinema.repository.FeedbackRepository;
import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.repository.SnackRepository;
import com.absolutecinema.repository.BookingSnackRepository;
import com.absolutecinema.repository.SessionRepository;
import com.absolutecinema.repository.LoyaltyAccountRepository;
import com.absolutecinema.entity.Movie;
import com.absolutecinema.entity.Snack;
import com.absolutecinema.entity.BookingSnack;
import com.absolutecinema.entity.Booking;
import com.absolutecinema.entity.Session;
import com.absolutecinema.entity.Feedback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

@Service
public class AnalyticsService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private SnackRepository snackRepository;

    @Autowired
    private BookingSnackRepository bookingSnackRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private LoyaltyAccountRepository loyaltyAccountRepository;

    public Map<String, Object> generateReport() {
        Map<String, Object> report = new HashMap<>();
        report.put("totalBookings", bookingRepository.count());
        report.put("totalFeedbacks", feedbackRepository.count());
        report.put("totalMovies", movieRepository.count());
        report.put("totalSnacks", snackRepository.count());
        return report;
    }

    public Map<String, Object> getAnalyticsData() {
        Map<String, Object> analyticsData = new HashMap<>();
        
        // Get all movies and calculate total tickets sold
        List<Movie> movies = movieRepository.findAll();
        int totalTicketsSold = movies.stream()
            .mapToInt(Movie::getTicketssold)
            .sum();
        
        // Get top 5 movies by ticket sales
        List<Movie> topMovies = movies.stream()
            .sorted((m1, m2) -> Integer.compare(m2.getTicketssold(), m1.getTicketssold()))
            .limit(5)
            .collect(Collectors.toList());

        // Get total snacks sold from Snack table for the stat card
        int totalSnacksSold = snackRepository.findAll().stream()
            .mapToInt(Snack::getSnackssold)
            .sum();

        // Count snacks from bookingsnacks table for the chart
        int monthlySnacksSold = bookingSnackRepository.findAll().stream()
            .mapToInt(BookingSnack::getQuantity)
            .sum();

        // Get monthly tickets sold from bookings table
        int monthlyTicketsSold = bookingRepository.findAll().stream()
            .mapToInt(Booking::getNumberOfTickets)
            .sum();
        
        // Get total number of bookings
        long totalBookings = bookingRepository.count();
        
        // Get total number of loyalty accounts
        long totalLoyaltyMembers = loyaltyAccountRepository.count();

        // Get most recent 7 feedbacks with movie titles
        List<Feedback> recentFeedbacks = feedbackRepository.findAllByOrderByCreatedAtDesc()
            .stream()
            .limit(7)
            .collect(Collectors.toList());
        
        // Create a map of movie IDs to titles for the feedback display
        Map<Long, String> movieTitles = recentFeedbacks.stream()
            .map(Feedback::getMovieId)
            .distinct()
            .collect(Collectors.toMap(
                movieId -> movieId,
                movieId -> movieRepository.findById(movieId)
                    .map(Movie::getTitle)
                    .orElse("Unknown Movie")
            ));
        
        analyticsData.put("totalTicketsSold", totalTicketsSold);
        analyticsData.put("topMovies", topMovies);
        analyticsData.put("totalSnacksSold", totalSnacksSold);
        analyticsData.put("totalBookings", totalBookings);
        analyticsData.put("monthlySnacksSold", monthlySnacksSold);
        analyticsData.put("monthlyTicketsSold", monthlyTicketsSold);
        analyticsData.put("totalLoyaltyMembers", totalLoyaltyMembers);
        analyticsData.put("recentFeedbacks", recentFeedbacks);
        analyticsData.put("movieTitles", movieTitles);
        
        return analyticsData;
    }

    // Additional analytics methods can be added here
}