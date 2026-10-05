package com.examportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examportal.dto.CreateQuestionRequest;
import com.examportal.dto.UpdateQuestionRequest;
import com.examportal.entity.Question;
import com.examportal.exception.QuestionNotFoundException;
import com.examportal.repository.QuestionRepository;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question createQuestion(CreateQuestionRequest request) {

        Question question = new Question();

        question.setQuestionText(request.getQuestionText());
        question.setOptionA(request.getOptionA());
        question.setOptionB(request.getOptionB());
        question.setOptionC(request.getOptionC());
        question.setOptionD(request.getOptionD());
        question.setCorrectAnswer(request.getCorrectAnswer());
        question.setDifficulty(request.getDifficulty());
        question.setCategory(request.getCategory());

        return questionRepository.save(question);
    }

    public List<Question> getAllQuestions() {

        return questionRepository.findAll();
    }

    public Question getQuestionById(Long id) {

        return questionRepository.findById(id)
                .orElseThrow(() ->
                        new QuestionNotFoundException(
                        		"Question not found with id: " + id));
    } 

    public void deleteQuestion(Long id) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() ->
                        new QuestionNotFoundException(
                        		"Question not found with id: " + id));

        questionRepository.delete(question);
    }
    
    public Question updateQuestion(
            Long id,
            UpdateQuestionRequest request) {

        Question question = questionRepository.findById(id)
                .orElseThrow(() ->
                        new QuestionNotFoundException(
                                "Question not found with id: " + id));

        question.setQuestionText(request.getQuestionText());
        question.setOptionA(request.getOptionA());
        question.setOptionB(request.getOptionB());
        question.setOptionC(request.getOptionC());
        question.setOptionD(request.getOptionD());
        question.setCorrectAnswer(request.getCorrectAnswer());
        question.setDifficulty(request.getDifficulty());
        question.setCategory(request.getCategory());

        return questionRepository.save(question);
    }
}