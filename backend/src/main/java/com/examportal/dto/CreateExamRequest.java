package com.examportal.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateExamRequest {

    @NotBlank(message = "Exam title is required")
    private String title;

    @NotBlank(message = "Exam description is required")
    private String description;

    @NotNull(message = "Duration is required")
    @Min(value = 1, message = "Duration must be greater than 0")
    private Integer durationInMinutes;

    @NotNull(message = "Total marks is required")
    @Min(value = 1, message = "Total marks must be greater than 0")
    private Integer totalMarks;

    @NotNull(message = "Passing marks are required")
    @Min(value = 1, message = "Passing marks must be greater than 0")
    private Integer passingMarks;
}
