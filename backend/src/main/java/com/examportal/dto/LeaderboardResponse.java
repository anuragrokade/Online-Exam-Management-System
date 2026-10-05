package com.examportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardResponse {

    private Integer rank;

    private Long userId;

    private String candidateName;

    private Integer score;

    private Double percentage;
}