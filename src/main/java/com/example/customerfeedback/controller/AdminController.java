package com.example.customerfeedback.controller;

import com.example.customerfeedback.service.FeedbackService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final FeedbackService feedbackService;

    public AdminController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // Admin dashboard
    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("feedbacks", feedbackService.findAll());
        return "admin/dashboard";
    }

    // Edit feedback page
    @GetMapping("/feedback/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("feedback", feedbackService.findById(id));
        return "admin/edit";
    }

    // Update feedback
    @PostMapping("/feedback/{id}/edit")
    public String update(
            @PathVariable Long id,
            @RequestParam String subject,
            @RequestParam String message,
            @RequestParam Integer rating) {

        feedbackService.update(id, subject, message, rating);

        return "redirect:/admin";
    }

    // Delete feedback
    @PostMapping("/feedback/{id}/delete")
    public String delete(@PathVariable Long id) {

        feedbackService.delete(id);

        return "redirect:/admin";
    }
}