package com.examportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examportal.dto.CreateExamRequest;
import com.examportal.entity.Exam;
import com.examportal.entity.Question;
import com.examportal.service.ExamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/exams")
@CrossOrigin(origins = "*")
public class ExamController {

    @Autowired
    private ExamService examService;

    @PostMapping
    public ResponseEntity<Exam> createExam(
            @Valid @RequestBody CreateExamRequest request) {

        return ResponseEntity.ok(
                examService.createExam(request));
    }

    @GetMapping
    public ResponseEntity<List<Exam>> getAllExams() {

        return ResponseEntity.ok(
                examService.getAllExams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExamById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                examService.getExamById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exam> updateExam(
            @PathVariable Long id,
            @Valid @RequestBody CreateExamRequest request) {

        return ResponseEntity.ok(
                examService.updateExam(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExam(
            @PathVariable Long id) {

        examService.deleteExam(id);

        return ResponseEntity.ok(
                "Exam deleted successfully");
    }
    
    @PostMapping("/{examId}/questions/{questionId}")
    public ResponseEntity<Exam> addQuestionToExam(
            @PathVariable Long examId,
            @PathVariable Long questionId) {

        return ResponseEntity.ok(
                examService.addQuestionToExam(
                        examId,
                        questionId));
    }
    
    @GetMapping("/{examId}/questions")
    public ResponseEntity<List<Question>> getExamQuestions(
            @PathVariable Long examId) {

        return ResponseEntity.ok(
                examService.getExamQuestions(examId));
    }
    
    @DeleteMapping("/{examId}/questions/{questionId}")
    public ResponseEntity<Exam> removeQuestionFromExam(
            @PathVariable Long examId,
            @PathVariable Long questionId) {

        return ResponseEntity.ok(
                examService.removeQuestionFromExam(
                        examId,
                        questionId));
    }
}