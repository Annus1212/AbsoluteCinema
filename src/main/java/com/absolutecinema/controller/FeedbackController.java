package com.absolutecinema.controller;

import com.absolutecinema.entity.Feedback;
import com.absolutecinema.entity.User;
import com.absolutecinema.service.FeedbackService;
import com.absolutecinema.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Feedback> submitFeedback(@RequestBody Map<String, Object> feedbackRequest) {
        // Get the authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        // Create and populate the feedback entity
        Feedback feedback = new Feedback();
        feedback.setUser(user);
        feedback.setMovieId(Long.valueOf(feedbackRequest.get("movieId").toString()));
        feedback.setRating(Integer.valueOf(feedbackRequest.get("rating").toString()));
        feedback.setComments(feedbackRequest.get("comments").toString());
        feedback.setCreatedAt(LocalDateTime.now());

        Feedback savedFeedback = feedbackService.saveFeedback(feedback);
        return ResponseEntity.ok(savedFeedback);
    }

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedbackList = feedbackService.getAllFeedback();
        return ResponseEntity.ok(feedbackList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedbackById(@PathVariable Long id) {
        Feedback feedback = feedbackService.getFeedbackById(id);
        return ResponseEntity.ok(feedback);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getFeedbackStatistics() {
        Map<String, Object> stats = feedbackService.getFeedbackStatistics();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/list")
    public List<Map<String, Object>> getFeedbackList() {
        return feedbackService.getFeedbackList();
    }
}