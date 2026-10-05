package com.examportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examportal.entity.Answer;
import com.examportal.entity.ExamAttempt;
import com.examportal.entity.Question;

@Repository
public interface AnswerRepository
        extends JpaRepository<Answer, Long> {

    /**
     * Get all answers for an exam attempt.
     */
    List<Answer> findByExamAttempt(
            ExamAttempt examAttempt);

    /**
     * Find answer for a specific question in a specific attempt.
     */
    Optional<Answer> findByExamAttemptAndQuestion(
            ExamAttempt examAttempt,
            Question question);

}
