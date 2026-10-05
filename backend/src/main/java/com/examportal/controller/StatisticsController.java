package com.examportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examportal.dto.ExamStatisticsResponse;
import com.examportal.service.StatisticsService;

@RestController
@RequestMapping("/api/statistics")
@CrossOrigin(origins = "*")
public class StatisticsController {

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/exams/{examId}")
    public ResponseEntity<ExamStatisticsResponse> getExamStatistics(
            @PathVariable Long examId) {

        return ResponseEntity.ok(
                statisticsService.getExamStatistics(examId));
    }
}