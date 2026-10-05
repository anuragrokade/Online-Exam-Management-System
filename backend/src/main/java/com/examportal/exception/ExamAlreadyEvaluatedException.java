package com.examportal.exception;

public class ExamAlreadyEvaluatedException extends RuntimeException {

    public ExamAlreadyEvaluatedException(String message) {
        super(message);
    }
}