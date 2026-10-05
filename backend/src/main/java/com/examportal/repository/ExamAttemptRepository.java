package com.examportal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examportal.enums.AttemptStatus;
import com.examportal.entity.Exam;
import com.examportal.entity.ExamAttempt;
import com.examportal.entity.User;

@Repository
public interface ExamAttemptRepository
        extends JpaRepository<ExamAttempt, Long> {

    /**
     * Get all attempts of a specific user.
     */
    List<ExamAttempt> findByUser(User user);

    /**
     * Get all attempts for a specific exam.
     */
    List<ExamAttempt> findByExam(Exam exam);

    /**
     * Get all attempts by status.
     */
    List<ExamAttempt> findByStatus(AttemptStatus status);

    /**
     * Find an attempt of a particular user for a particular exam.
     */
    Optional<ExamAttempt> findByExamAndUser(
            Exam exam,
            User user);

}