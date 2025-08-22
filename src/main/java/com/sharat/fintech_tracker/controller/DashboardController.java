package com.sharat.fintech_tracker.controller;

import com.sharat.fintech_tracker.dto.DashboardResponseDTO;
import com.sharat.fintech_tracker.service.DashboardService;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardResponseDTO getDashboardSummary(@AuthenticationPrincipal User user) {
        return dashboardService.getDashboardSummary(user);
    }
}
