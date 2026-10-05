package com.examportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examportal.dto.SubmitAnswerRequest;
import com.examportal.entity.Answer;
import com.examportal.service.AnswerService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/answers")
@CrossOrigin(origins = "http://localhost:4200")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping
    public ResponseEntity<Answer> submitAnswer(
            @Valid @RequestBody SubmitAnswerRequest request) {

        return ResponseEntity.ok(
                answerService.submitAnswer(request));
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<Answer> getAnswerById(
            @PathVariable Long answerId) {

        return ResponseEntity.ok(
                answerService.getAnswerById(answerId));
    }

    @GetMapping("/attempt/{attemptId}")
    public ResponseEntity<List<Answer>> getAnswersByAttempt(
            @PathVariable Long attemptId) {

        return ResponseEntity.ok(
                answerService.getAnswersByAttempt(attemptId));
    }
}