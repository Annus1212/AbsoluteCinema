package com.absolutecinema.service;

import com.absolutecinema.entity.Feedback;
import com.absolutecinema.entity.Movie;
import com.absolutecinema.repository.FeedbackRepository;
import com.absolutecinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    private final FeedbackRepository feedbackRepository;
    @Autowired
    private final MovieRepository movieRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, MovieRepository movieRepository) {
        this.feedbackRepository = feedbackRepository;
        this.movieRepository = movieRepository;
    }

    public Feedback submitFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    public void deleteFeedback(Long id) {
        feedbackRepository.deleteById(id);
    }

    public Feedback getFeedbackById(Long id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public Feedback saveFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public Map<String, Object> getFeedbackStatistics() {
        List<Feedback> allFeedback = feedbackRepository.findAll();
        int totalFeedback = allFeedback.size();
        int positiveFeedback = (int) allFeedback.stream()
            .filter(f -> f.getRating() >= 3)
            .count();
        int negativeFeedback = totalFeedback - positiveFeedback;

        // Get feedback for the last 30 days
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        List<Feedback> recentFeedback = allFeedback.stream()
            .filter(f -> f.getCreatedAt().isAfter(thirtyDaysAgo))
            .collect(Collectors.toList());

        // Group feedback by day
        Map<LocalDate, List<Feedback>> feedbackByDay = recentFeedback.stream()
            .collect(Collectors.groupingBy(f -> f.getCreatedAt().toLocalDate()));

        // Generate labels and average ratings for the last 30 days
        List<String> labels = new ArrayList<>();
        List<Double> averageRatings = new ArrayList<>();

        LocalDate startDate = LocalDate.now().minusDays(29);
        for (int i = 0; i < 30; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            labels.add(currentDate.format(DateTimeFormatter.ofPattern("MMM d")));
            
            List<Feedback> dayFeedback = feedbackByDay.getOrDefault(currentDate, Collections.emptyList());
            double averageRating = dayFeedback.isEmpty() ? 0 : 
                dayFeedback.stream()
                    .mapToInt(Feedback::getRating)
                    .average()
                    .orElse(0);
            
            averageRatings.add(averageRating);
        }

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalFeedback", totalFeedback);
        stats.put("positiveFeedback", positiveFeedback);
        stats.put("negativeFeedback", negativeFeedback);
        stats.put("chartData", Map.of(
            "labels", labels,
            "averageRatings", averageRatings
        ));
        
        return stats;
    }

    public List<Map<String, Object>> getFeedbackList() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks.stream()
            .map(feedback -> {
                Map<String, Object> feedbackData = new HashMap<>();
                feedbackData.put("id", feedback.getId());
                feedbackData.put("rating", feedback.getRating());
                feedbackData.put("comment", feedback.getComments());
                feedbackData.put("createdAt", feedback.getCreatedAt());
                
                // Get movie title
                Movie movie = movieRepository.findById(feedback.getMovieId()).orElse(null);
                feedbackData.put("movieTitle", movie != null ? movie.getTitle() : "Unknown Movie");
                
                // Get username
                if (feedback.getUser() != null) {
                    String userName = feedback.getUser().getFirstName() + " " + feedback.getUser().getLastName();
                    feedbackData.put("userName", userName);
                } else {
                    feedbackData.put("userName", "Anonymous User");
                }
                
                // Determine sentiment badge
                if (feedback.getRating() >= 4) {
                    feedbackData.put("sentiment", "Positive");
                } else if (feedback.getRating() <= 2) {
                    feedbackData.put("sentiment", "Negative");
                } else {
                    feedbackData.put("sentiment", "Neutral");
                }
                
                return feedbackData;
            })
            .collect(Collectors.toList());
    }
}