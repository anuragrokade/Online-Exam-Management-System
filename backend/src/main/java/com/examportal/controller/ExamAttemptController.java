package com.examportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examportal.dto.RemainingTimeResponse;
import com.examportal.dto.StartExamRequest;
import com.examportal.entity.ExamAttempt;
import com.examportal.service.ExamAttemptService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/exam-attempts")
@CrossOrigin(origins = "http://localhost:4200")
public class ExamAttemptController {

    @Autowired
    private ExamAttemptService examAttemptService;

    @PostMapping("/start")
    public ResponseEntity<ExamAttempt> startExam(
            @Valid @RequestBody StartExamRequest request) {

        return ResponseEntity.ok(
                examAttemptService.startExam(request));
    }

    @GetMapping("/{attemptId}")
    public ResponseEntity<ExamAttempt> getAttemptById(
            @PathVariable Long attemptId) {

        return ResponseEntity.ok(
                examAttemptService.getAttemptById(attemptId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExamAttempt>> getAttemptsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                examAttemptService.getAttemptsByUser(userId));
    }

    @PostMapping("/{attemptId}/submit")
    public ResponseEntity<ExamAttempt> submitExam(
            @PathVariable Long attemptId) {

        return ResponseEntity.ok(
                examAttemptService.submitExam(attemptId));
    }
    
    @GetMapping("/{attemptId}/remaining-time")
    public ResponseEntity<RemainingTimeResponse>
    getRemainingTime(
            @PathVariable Long attemptId) {

        return ResponseEntity.ok(
                examAttemptService.getRemainingTime(
                        attemptId));
    }
}