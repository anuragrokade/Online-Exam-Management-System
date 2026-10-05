package com.examportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemainingTimeResponse {

    private Long attemptId;

    private Long remainingMinutes;

    private boolean expired;
}