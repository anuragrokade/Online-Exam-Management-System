package com.examportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateDashboardResponse {

    private Long userId;

    private Long totalExamsAttempted;

    private Long passedExams;

    private Long failedExams;

    private Double averagePercentage;

}