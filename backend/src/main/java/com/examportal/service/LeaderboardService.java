package com.examportal.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examportal.dto.LeaderboardResponse;
import com.examportal.entity.Exam;
import com.examportal.entity.Result;
import com.examportal.entity.User;
import com.examportal.exception.ExamNotFoundException;
import com.examportal.repository.ExamRepository;
import com.examportal.repository.ResultRepository;

@Service
public class LeaderboardService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ResultRepository resultRepository;

    public List<LeaderboardResponse> getLeaderboard(
            Long examId) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new ExamNotFoundException(
                                "Exam not found with id: " + examId));

        List<Result> results =
                resultRepository.findByExamAttempt_Exam(exam);

        results.sort(
                Comparator
                        .comparing(Result::getPercentage)
                        .reversed()
                        .thenComparing(Result::getScore)
                        .reversed());

        List<LeaderboardResponse> leaderboard =
                new ArrayList<>();

        int currentRank = 1;
        int position = 1;
        Double previousPercentage = null;

        for (Result result : results) {

            if (previousPercentage != null
                    && Double.compare(
                            result.getPercentage(),
                            previousPercentage) != 0) {

                currentRank = position;
            }

            User user = result.getExamAttempt().getUser();

            leaderboard.add(
                    new LeaderboardResponse(
                            currentRank,
                            user.getId(),
                            user.getFirstName()
                                    + " "
                                    + user.getLastName(),
                            result.getScore(),
                            result.getPercentage()));

            previousPercentage = result.getPercentage();
            position++;
        }

        return leaderboard;
    }
}