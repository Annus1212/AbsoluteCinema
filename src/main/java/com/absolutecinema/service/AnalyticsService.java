package com.absolutecinema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.absolutecinema.repository.BookingRepository;
import com.absolutecinema.repository.FeedbackRepository;
import com.absolutecinema.repository.MovieRepository;
import com.absolutecinema.repository.SnackRepository;

import java.util.HashMap;
import java.util.Map;

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
        // Populate with relevant analytics data
        // For example:
        analyticsData.put("totalMovies", 0);
        analyticsData.put("totalBookings", 0);
        analyticsData.put("revenue", 0.0);
        return analyticsData;
    }

    // Additional analytics methods can be added here
}