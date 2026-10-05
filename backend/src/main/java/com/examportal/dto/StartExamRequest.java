package com.examportal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StartExamRequest {

    @NotNull(message = "Exam ID is required")
    private Long examId;

    @NotNull(message = "User ID is required")
    private Long userId;

}