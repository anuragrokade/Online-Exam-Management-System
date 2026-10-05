package com.examportal.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examportal.dto.SubmitAnswerRequest;
import com.examportal.entity.Answer;
import com.examportal.entity.ExamAttempt;
import com.examportal.entity.Question;
import com.examportal.enums.AttemptStatus;
import com.examportal.exception.ExamAttemptNotFoundException;
import com.examportal.exception.QuestionNotFoundException;
import com.examportal.repository.AnswerRepository;
import com.examportal.repository.ExamAttemptRepository;
import com.examportal.repository.QuestionRepository;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private ExamAttemptRepository examAttemptRepository;

    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private ExamAttemptService examAttemptService;
    
    private boolean isExamExpired(
            ExamAttempt attempt) {

        LocalDateTime expiryTime =
                attempt.getStartTime()
                        .plusMinutes(
                                attempt.getExam()
                                        .getDurationInMinutes());

        return LocalDateTime.now().isAfter(expiryTime);
    }

    public Answer submitAnswer(
            SubmitAnswerRequest request) {

        ExamAttempt attempt =
                examAttemptRepository.findById(
                        request.getAttemptId())
                        .orElseThrow(() ->
                                new ExamAttemptNotFoundException(
                                        "Exam attempt not found with id: "
                                                + request.getAttemptId()));
        examAttemptService.autoSubmitIfExpired(attempt);

        if (attempt.getStatus() != AttemptStatus.IN_PROGRESS) {
            throw new RuntimeException(
                    "Exam has already been submitted");
        }

        
        Question question =
                questionRepository.findById(
                        request.getQuestionId())
                        .orElseThrow(() ->
                                new QuestionNotFoundException(
                                        "Question not found with id: "
                                                + request.getQuestionId()));

        Optional<Answer> existingAnswer =
                answerRepository.findByExamAttemptAndQuestion(
                        attempt,
                        question);

        if (existingAnswer.isPresent()) {

            Answer answer = existingAnswer.get();

            answer.setSelectedAnswer(
                    request.getSelectedAnswer());

            return answerRepository.save(answer);
        }

        Answer answer = new Answer();

        answer.setExamAttempt(attempt);
        answer.setQuestion(question);
        answer.setSelectedAnswer(
                request.getSelectedAnswer());

        return answerRepository.save(answer);
        
    }

    public List<Answer> getAnswersByAttempt(
            Long attemptId) {

        ExamAttempt attempt =
                examAttemptRepository.findById(attemptId)
                        .orElseThrow(() ->
                                new ExamAttemptNotFoundException(
                                        "Exam attempt not found with id: "
                                                + attemptId));

        return answerRepository.findByExamAttempt(
                attempt);
    }

    public Answer getAnswerById(Long answerId) {

        return answerRepository.findById(answerId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Answer not found with id: "
                                        + answerId));
    }
}