package com.examportal.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examportal.dto.RemainingTimeResponse;
import com.examportal.dto.StartExamRequest;
import com.examportal.enums.AttemptStatus;
import com.examportal.entity.Exam;
import com.examportal.entity.ExamAttempt;
import com.examportal.entity.User;
import com.examportal.exception.ExamAttemptNotFoundException;
import com.examportal.exception.ExamNotFoundException;
import com.examportal.repository.ExamAttemptRepository;
import com.examportal.repository.ExamRepository;
import com.examportal.repository.UserRepository;

@Service
public class ExamAttemptService {

    @Autowired
    private ExamAttemptRepository examAttemptRepository;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserRepository userRepository;

    public ExamAttempt startExam(StartExamRequest request) {

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() ->
                        new ExamNotFoundException(
                                "Exam not found with id: "
                                        + request.getExamId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with id: "
                                        + request.getUserId()));
        if (examAttemptRepository
    	        .findByExamAndUser(exam, user)
    	        .isPresent()) {

    	    throw new RuntimeException(
    	            "User has already started this exam");
    	}

        ExamAttempt attempt = new ExamAttempt();

        attempt.setExam(exam);
        attempt.setUser(user);
        attempt.setStartTime(LocalDateTime.now());
        attempt.setStatus(AttemptStatus.IN_PROGRESS);

        return examAttemptRepository.save(attempt);
    }

    public ExamAttempt getAttemptById(Long id) {

        return examAttemptRepository.findById(id)
                .orElseThrow(() ->
                        new ExamAttemptNotFoundException(
                                "Exam attempt not found with id: "
                                        + id));
    }

    public List<ExamAttempt> getAttemptsByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with id: "
                                        + userId));

        return examAttemptRepository.findByUser(user);
    }

    public ExamAttempt submitExam(Long attemptId) {

        ExamAttempt attempt =
                examAttemptRepository.findById(attemptId)
                        .orElseThrow(() ->
                                new ExamAttemptNotFoundException(
                                        "Exam attempt not found with id: "
                                                + attemptId));

        autoSubmitIfExpired(attempt);

        if (attempt.getStatus() != AttemptStatus.IN_PROGRESS) {
            throw new RuntimeException(
                    "Exam has already been submitted");
        }

        attempt.setEndTime(LocalDateTime.now());
        attempt.setStatus(AttemptStatus.SUBMITTED);

        return examAttemptRepository.save(attempt);
    }
    
    private boolean isExamExpired(
            ExamAttempt attempt) {

        LocalDateTime expiryTime =
                attempt.getStartTime()
                        .plusMinutes(
                                attempt.getExam()
                                        .getDurationInMinutes());

        return LocalDateTime.now()
                .isAfter(expiryTime);
    }
    
    public void autoSubmitIfExpired(
            ExamAttempt attempt) {

        if (attempt.getStatus() != AttemptStatus.IN_PROGRESS) {
            return;
        }

        if (isExamExpired(attempt)) {

            attempt.setEndTime(
                    attempt.getStartTime()
                            .plusMinutes(
                                    attempt.getExam()
                                            .getDurationInMinutes()));

            attempt.setStatus(
                    AttemptStatus.SUBMITTED);

            examAttemptRepository.save(attempt);
        }
    }
    
    public RemainingTimeResponse getRemainingTime(
            Long attemptId) {

        ExamAttempt attempt =
                examAttemptRepository.findById(attemptId)
                        .orElseThrow(() ->
                                new ExamAttemptNotFoundException(
                                        "Exam attempt not found with id: "
                                                + attemptId));

        autoSubmitIfExpired(attempt);

        long duration =
                attempt.getExam()
                        .getDurationInMinutes();

        LocalDateTime expiryTime =
                attempt.getStartTime()
                        .plusMinutes(duration);

        long remainingMinutes =
                Duration.between(
                        LocalDateTime.now(),
                        expiryTime)
                        .toMinutes();

        if (remainingMinutes < 0) {
            remainingMinutes = 0;
        }

        return new RemainingTimeResponse(
                attempt.getId(),
                remainingMinutes,
                attempt.getStatus() != AttemptStatus.IN_PROGRESS);
    }
    
}