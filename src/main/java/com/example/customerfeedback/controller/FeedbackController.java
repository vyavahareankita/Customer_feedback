package com.example.customerfeedback.controller;

import com.example.customerfeedback.service.FeedbackService;
import com.example.customerfeedback.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final UserService userService;

    public FeedbackController(
            FeedbackService feedbackService,
            UserService userService) {

        this.feedbackService = feedbackService;
        this.userService = userService;
    }

    // Show user's feedback
    @GetMapping
    public String list(
            Authentication auth,
            Model model) {

        var user = userService.findByEmail(auth.getName());

        model.addAttribute(
                "feedbacks",
                feedbackService.findByUser(user.getId())
        );

        model.addAttribute("user", user);

        return "feedback/list";
    }

    // New feedback form
    @GetMapping("/new")
    public String newFeedback(Model model) {

        model.addAttribute("feedback", new FormData());

        return "feedback/form";
    }

    // Create feedback
    @PostMapping
    public String create(
            Authentication auth,
            @RequestParam String subject,
            @RequestParam String message,
            @RequestParam Integer rating) {

        var user = userService.findByEmail(auth.getName());

        feedbackService.create(
                subject,
                message,
                rating,
                user
        );

        return "redirect:/feedback";
    }

    // Edit feedback page
    @GetMapping("/{id}/edit")
    public String edit(
            @PathVariable Long id,
            Authentication auth,
            Model model) {

        var feedback = feedbackService.findById(id);
        var user = userService.findByEmail(auth.getName());

        // Make sure the feedback belongs to the logged-in user
        if (!feedback.getUser().getId().equals(user.getId())) {
            return "redirect:/feedback";
        }

        model.addAttribute("feedback", feedback);

        return "feedback/edit";
    }

    // Update feedback
    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            Authentication auth,
            @RequestParam String subject,
            @RequestParam String message,
            @RequestParam Integer rating) {

        var feedback = feedbackService.findById(id);
        var user = userService.findByEmail(auth.getName());

        // Only owner can update
        if (feedback.getUser().getId().equals(user.getId())) {

            feedbackService.update(
                    id,
                    subject,
                    message,
                    rating
            );
        }

        return "redirect:/feedback";
    }

    public static class FormData {
    }
}