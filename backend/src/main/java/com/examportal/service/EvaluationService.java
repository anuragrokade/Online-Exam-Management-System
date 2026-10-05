package com.examportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examportal.dto.ResultResponse;
import com.examportal.entity.Answer;
import com.examportal.enums.AttemptStatus;
import com.examportal.entity.ExamAttempt;
import com.examportal.entity.Result;
import com.examportal.exception.ExamAlreadyEvaluatedException;
import com.examportal.exception.ExamAttemptNotFoundException;
import com.examportal.repository.AnswerRepository;
import com.examportal.repository.ExamAttemptRepository;
import com.examportal.repository.ResultRepository;

/*
 * Candidate Examination Workflow:
 *
 * 1. Candidate starts an exam attempt.
 * 2. Candidate submits answers for exam questions.
 * 3. Candidate submits the completed exam.
 * 4. System evaluates all submitted answers.
 * 5. System calculates score and percentage.
 * 6. System determines PASS or FAIL status.
 * 7. System stores the result in the RESULTS table.
 * 8. System returns a ResultResponse to the client.
 */

@Service
public class EvaluationService {

	@Autowired
	private ExamAttemptRepository examAttemptRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Autowired
	private ResultRepository resultRepository;

	public ResultResponse evaluateAttempt(Long attemptId) {

		ExamAttempt attempt = examAttemptRepository.findById(attemptId)
				.orElseThrow(() -> new ExamAttemptNotFoundException("Exam attempt not found with id: " + attemptId));

		if (attempt.getStatus() == AttemptStatus.EVALUATED) {
			throw new ExamAlreadyEvaluatedException(
					"Exam attempt with id " + attemptId + " has already been evaluated");
		}

		if (attempt.getStatus() != AttemptStatus.SUBMITTED) {
			throw new RuntimeException("Only submitted attempts can be evaluated");
		}

		List<Answer> answers = answerRepository.findByExamAttempt(attempt);

		int totalQuestions = answers.size();
		int correctAnswers = 0;
		int score = 0;

		for (Answer answer : answers) {

			String selectedAnswer = answer.getSelectedAnswer();

			String correctAnswer = answer.getQuestion().getCorrectAnswer();

			if (selectedAnswer.equalsIgnoreCase(correctAnswer)) {

				answer.setCorrect(true);
				answer.setMarksAwarded(1);

				correctAnswers++;
				score++;

			} else {

				answer.setCorrect(false);
				answer.setMarksAwarded(0);
			}

			answerRepository.save(answer);
		}

		double percentage = 0.0;

		if (totalQuestions > 0) {
			percentage = ((double) score / totalQuestions) * 100;
		}

		Integer passingPercentage = attempt.getExam().getPassingMarks();

		String resultStatus = percentage >= passingPercentage ? "PASS" : "FAIL";

		attempt.setScore(score);
		attempt.setStatus(AttemptStatus.EVALUATED);

		examAttemptRepository.save(attempt);

		/*
		 * Save Result
		 */
		
		if (resultRepository.findByExamAttempt(attempt).isPresent()) {
		    throw new RuntimeException(
		            "Result already exists for this attempt");
		}
		Result result = new Result();

		result.setExamAttempt(attempt);
		result.setTotalQuestions(totalQuestions);
		result.setCorrectAnswers(correctAnswers);
		result.setScore(score);
		result.setPercentage(percentage);
		result.setResultStatus(resultStatus);

		resultRepository.save(result);

		return new ResultResponse(attempt.getId(), totalQuestions, correctAnswers, score, percentage, resultStatus);
	}
}