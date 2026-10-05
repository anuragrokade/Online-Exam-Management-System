package com.examportal.entity;

import java.time.LocalDateTime;

import com.examportal.enums.AttemptStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exam_attempts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "exam_id",
            nullable = false)
    private Exam exam;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer score;

    @Enumerated(EnumType.STRING)
    private AttemptStatus status;

    @PrePersist
    public void prePersist() {

        startTime = LocalDateTime.now();

        if (status == null) {
            status = AttemptStatus.IN_PROGRESS;
        }
    }
}