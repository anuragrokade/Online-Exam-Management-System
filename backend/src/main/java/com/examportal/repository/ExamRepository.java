package com.examportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.examportal.entity.Exam;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

}