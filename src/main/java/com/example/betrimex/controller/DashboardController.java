package com.example.betrimex.controller;

import com.example.betrimex.model.dto.response.DashboardStatResponse;
import com.example.betrimex.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
public class DashboardController {


    private final DashboardService dashboardService;

    @GetMapping("/get-dashboard-stats")
    public DashboardStatResponse getDashboardStats() {
        return dashboardService.getDashboardStat();
    }
}
