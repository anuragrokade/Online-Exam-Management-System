package com.examportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.examportal.entity.ExamAttempt;
import com.examportal.entity.Result;
import com.examportal.entity.User;
import com.examportal.exception.ExamAttemptNotFoundException;
import com.examportal.exception.ResultNotFoundException;
import com.examportal.repository.ExamAttemptRepository;
import com.examportal.repository.ResultRepository;
import com.examportal.repository.UserRepository;

@Service
public class ResultService {

    @Autowired 
    private ResultRepository resultRepository;

    @Autowired
    private ExamAttemptRepository examAttemptRepository;

    @Autowired
    private UserRepository userRepository;

    public Result getResultById(Long resultId) {

        return resultRepository.findById(resultId)
                .orElseThrow(() ->
                        new ResultNotFoundException(
                                "Result not found with id: "
                                        + resultId));
    }

    public Result getResultByAttemptId(Long attemptId) {

        ExamAttempt attempt =
                examAttemptRepository.findById(attemptId)
                        .orElseThrow(() ->
                                new ExamAttemptNotFoundException(
                                        "Exam attempt not found with id: "
                                                + attemptId));

        return resultRepository.findByExamAttempt(attempt)
                .orElseThrow(() ->
                        new ResultNotFoundException(
                                "Result not found for attempt id: "
                                        + attemptId));
    }

    public List<Result> getResultsByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with id: "
                                        + userId));

        List<ExamAttempt> attempts =
                examAttemptRepository.findByUser(user);

        return attempts.stream()
                .map(attempt ->
                        resultRepository.findByExamAttempt(attempt)
                                .orElse(null))
                .filter(result -> result != null)
                .toList();
    }
    
    public Page<Result> getResultsByUser(
            Long userId,
            int page,
            int size) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found with id: "
                                        + userId));

        Pageable pageable =
                PageRequest.of(page, size);

        return resultRepository.findByExamAttempt_User(
                user,
                pageable);
    } 
}