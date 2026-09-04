package com.prepsprint.controller;

import com.prepsprint.dto.DashboardStatsDto;
import com.prepsprint.entity.User;
import com.prepsprint.repository.UserRepository;
import com.prepsprint.service.DashboardService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;
    private final UserRepository userRepository;

    public DashboardController(DashboardService dashboardService, UserRepository userRepository) {
        this.dashboardService = dashboardService;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard(Authentication authentication, Model model) {
        String email = authentication != null ? authentication.getName() : "student@prepsprint.com";
        User user = userRepository.findByEmail(email).orElse(null);

        Long userId = user != null ? user.getId() : 1L;
        DashboardStatsDto stats = dashboardService.getStats(userId);

        model.addAttribute("user", user);
        model.addAttribute("stats", stats);
        model.addAttribute("activeNav", "dashboard");

        // Static Recommendation Placeholder for Prompt 8
        model.addAttribute("recReason", "SQL Joins accuracy is low at 42% — practice 10 targeted questions.");
        model.addAttribute("recEstimatedTime", "15 mins");
        model.addAttribute("recTargetTopic", "SQL Queries & Joins");

        return "dashboard/index";
    }
}
