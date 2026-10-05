package com.examportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponse {

    private Long attemptId;

    private Integer totalQuestions;

    private Integer correctAnswers;

    private Integer score;

    private Double percentage;

    private String result;

}