package com.examportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examportal.entity.Result;
import com.examportal.service.ResultService;

@RestController
@RequestMapping("/api/results")
@CrossOrigin(origins = "*")
public class ResultController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/{resultId}")
    public ResponseEntity<Result> getResultById(
            @PathVariable Long resultId) {

        return ResponseEntity.ok(
                resultService.getResultById(resultId));
    }

    @GetMapping("/attempt/{attemptId}")
    public ResponseEntity<Result> getResultByAttemptId(
            @PathVariable Long attemptId) {

        return ResponseEntity.ok(
                resultService.getResultByAttemptId(attemptId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Result>> getResultsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                resultService.getResultsByUser(
                        userId,
                        page,
                        size));
    }
}