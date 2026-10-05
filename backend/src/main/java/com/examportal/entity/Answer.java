package com.examportal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "answers")
@Data   
@NoArgsConstructor
@AllArgsConstructor
public class Answer { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String selectedAnswer;

    private Boolean correct;

    private Integer marksAwarded;

    @ManyToOne
    @JoinColumn(
            name = "attempt_id",
            nullable = false)
    private ExamAttempt examAttempt;

    @ManyToOne
    @JoinColumn(
            name = "question_id",
            nullable = false)
    private Question question;
}