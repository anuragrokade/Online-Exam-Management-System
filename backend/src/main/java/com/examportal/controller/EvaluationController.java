package com.examportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examportal.dto.ResultResponse;
import com.examportal.service.EvaluationService;

@RestController
@RequestMapping("/api/evaluation")
@CrossOrigin(origins = "*")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @PostMapping("/{attemptId}")
    public ResponseEntity<ResultResponse> evaluateAttempt(
            @PathVariable Long attemptId) {

        return ResponseEntity.ok(
                evaluationService.evaluateAttempt(attemptId));
    }
}