package com.examportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examportal.dto.ExamStatisticsResponse;
import com.examportal.entity.Exam;
import com.examportal.entity.Result;
import com.examportal.exception.ExamNotFoundException;
import com.examportal.repository.ExamRepository;
import com.examportal.repository.ResultRepository;

@Service
public class StatisticsService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ResultRepository resultRepository;

    public ExamStatisticsResponse getExamStatistics(
            Long examId) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new ExamNotFoundException(
                                "Exam not found with id: "
                                        + examId));

        List<Result> results =
                resultRepository.findByExamAttempt_Exam(exam);

        long totalAttempts = results.size();

        long passedCandidates = results.stream()
                .filter(result ->
                        "PASS".equalsIgnoreCase(
                                result.getResultStatus()))
                .count();

        long failedCandidates =
                totalAttempts - passedCandidates;

        double passPercentage = 0.0;

        if (totalAttempts > 0) {
            passPercentage =
                    (passedCandidates * 100.0)
                            / totalAttempts;
        }

        double averageScore = results.stream()
                .mapToInt(Result::getScore)
                .average()
                .orElse(0.0);

        return new ExamStatisticsResponse(
                exam.getId(),
                exam.getTitle(),
                totalAttempts,
                passedCandidates,
                failedCandidates,
                passPercentage,
                averageScore);
    }
}