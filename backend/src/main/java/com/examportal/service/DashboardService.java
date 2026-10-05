package com.examportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examportal.dto.CandidateDashboardResponse;
import com.examportal.entity.ExamAttempt;
import com.examportal.entity.Result;
import com.examportal.entity.User;
import com.examportal.repository.ExamAttemptRepository;
import com.examportal.repository.ResultRepository;
import com.examportal.repository.UserRepository;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExamAttemptRepository examAttemptRepository;

    @Autowired
    private ResultRepository resultRepository;

    public CandidateDashboardResponse getDashboard(
            Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with id: "
                                        + userId));

        List<ExamAttempt> attempts =
                examAttemptRepository.findByUser(user);

        List<Result> results = attempts.stream()
                .map(attempt ->
                        resultRepository.findByExamAttempt(attempt)
                                .orElse(null))
                .filter(result -> result != null)
                .toList();

        long totalExamsAttempted =
                results.size();

        long passedExams = results.stream()
                .filter(result ->
                        "PASS".equalsIgnoreCase(
                                result.getResultStatus()))
                .count();

        long failedExams =
                totalExamsAttempted - passedExams;

        double averagePercentage =
                results.stream()
                        .mapToDouble(Result::getPercentage)
                        .average()
                        .orElse(0.0);

        return new CandidateDashboardResponse(
                userId,
                totalExamsAttempted,
                passedExams,
                failedExams,
                averagePercentage);
    }
}