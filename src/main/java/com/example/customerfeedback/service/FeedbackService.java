package com.example.customerfeedback.service;

import com.example.customerfeedback.model.Feedback;
import com.example.customerfeedback.model.User;
import com.example.customerfeedback.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public List<Feedback> findAll() {
        return feedbackRepository.findAll();
    }

    public List<Feedback> findByUser(Long userId) {
        return feedbackRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Feedback findById(Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Feedback not found."));
    }

    public Feedback create(String subject, String message, Integer rating, User user) {
        Feedback feedback = new Feedback();
        feedback.setSubject(subject);
        feedback.setMessage(message);
        feedback.setRating(rating);
        feedback.setUser(user);
        return feedbackRepository.save(feedback);
    }

    public Feedback update(Long id, String subject, String message, Integer rating) {
        Feedback feedback = findById(id);
        feedback.setSubject(subject);
        feedback.setMessage(message);
        feedback.setRating(rating);
        return feedbackRepository.save(feedback);
    }

    public void delete(Long id) {
        feedbackRepository.deleteById(id);
    }
}
