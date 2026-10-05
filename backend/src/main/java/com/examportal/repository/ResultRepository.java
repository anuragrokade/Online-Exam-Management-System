package com.examportal.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examportal.entity.Exam;
import com.examportal.entity.ExamAttempt;
import com.examportal.entity.Result;
import com.examportal.entity.User;

public interface ResultRepository
        extends JpaRepository<Result, Long> {

    Optional<Result> findByExamAttempt(
            ExamAttempt examAttempt);

    Page<Result> findByExamAttempt_User(
            User user,
            Pageable pageable);
    
/*
 Result
   ↓
examAttempt
   ↓
exam
 */

    List<Result> findByExamAttempt_Exam(
            Exam exam);

}