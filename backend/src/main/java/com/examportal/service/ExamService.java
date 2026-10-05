package com.examportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examportal.dto.CreateExamRequest;
import com.examportal.entity.Exam;
import com.examportal.entity.Question;
import com.examportal.exception.ExamNotFoundException;
import com.examportal.exception.QuestionNotAssignedException;
import com.examportal.exception.QuestionNotFoundException;
import com.examportal.repository.ExamRepository;
import com.examportal.repository.QuestionRepository;

@Service
public class ExamService {

    @Autowired 
    private ExamRepository examRepository;
    
    @Autowired
    private QuestionRepository questionRepository;

    public Exam createExam(CreateExamRequest request) {

        Exam exam = new Exam();

        exam.setTitle(request.getTitle());
        exam.setDescription(request.getDescription());
        exam.setDurationInMinutes(request.getDurationInMinutes());
        exam.setTotalMarks(request.getTotalMarks());
        exam.setPassingMarks(request.getPassingMarks());

        return examRepository.save(exam); 
    }

    public List<Exam> getAllExams() {

        return examRepository.findAll();
    }

    public Exam getExamById(Long id) {

        return examRepository.findById(id)
                .orElseThrow(() ->
                        new ExamNotFoundException(
                                "Exam not found with id: " + id));
    }

    public Exam updateExam(
            Long id,
            CreateExamRequest request) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(() ->
                        new ExamNotFoundException(
                                "Exam not found with id: " + id));

        exam.setTitle(request.getTitle());
        exam.setDescription(request.getDescription());
        exam.setDurationInMinutes(request.getDurationInMinutes());
        exam.setTotalMarks(request.getTotalMarks());
        exam.setPassingMarks(request.getPassingMarks());

        return examRepository.save(exam);
    }

    public void deleteExam(Long id) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(() ->
                        new ExamNotFoundException(
                                "Exam not found with id: " + id));

        examRepository.delete(exam);
    }
    
    public Exam addQuestionToExam(
            Long examId,
            Long questionId) {

    	
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new ExamNotFoundException(
                                "Exam not found with id: " + examId));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new QuestionNotFoundException(
                                "Question not found with id: " + questionId));
        
        if (exam.getQuestions().contains(question)) {
            throw new RuntimeException(
                    "Question is already assigned to this exam");
        }

        exam.getQuestions().add(question);

        return examRepository.save(exam);
    }
    
    public List<Question> getExamQuestions(Long examId) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new ExamNotFoundException(
                                "Exam not found with id: " + examId));

        return exam.getQuestions();
    }
    
    public Exam removeQuestionFromExam(
            Long examId,
            Long questionId) {

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() ->
                        new ExamNotFoundException(
                                "Exam not found with id: " + examId));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() ->
                        new QuestionNotFoundException(
                                "Question not found with id: " + questionId));

        if (!exam.getQuestions().contains(question)) {
            throw new QuestionNotAssignedException(
                    "Question with id " + questionId
                            + " is not assigned to exam with id "
                            + examId);
        }

        exam.getQuestions().remove(question);

        return examRepository.save(exam);
    }
}