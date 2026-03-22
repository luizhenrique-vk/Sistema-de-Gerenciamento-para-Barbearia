package com.luizhenriue.crudapi.controller;

import com.luizhenriue.crudapi.dto.dashboard.DashboardResponse;
import com.luizhenriue.crudapi.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardResponse summary() {
        return dashboardService.getSummary();
    }
}
