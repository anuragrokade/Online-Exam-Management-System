package com.examportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamStatisticsResponse {

    private Long examId;

    private String examTitle;

    private Long totalAttempts;

    private Long passedCandidates;

    private Long failedCandidates;

    private Double passPercentage;

    private Double averageScore;

}