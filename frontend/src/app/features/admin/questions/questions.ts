import { Component, OnInit } from '@angular/core';

import { CommonModule } from '@angular/common';

import { Question } from '../../../core/models/question.model';

import { QuestionService } from '../../../core/services/question.service';

@Component({
  selector: 'app-questions',

  standalone: true,

  imports: [
    CommonModule
  ],

  templateUrl: './questions.html',

  styleUrl: './questions.css'
})
export class QuestionsComponent implements OnInit {

  questions: Question[] = []; //Stores all questions received from the backend.

  constructor(
    private questionService: QuestionService
  ) { }

  ngOnInit(): void {

    this.loadQuestions();

  }

  loadQuestions(): void {  //Loads questions from backend.

    this.questionService
      .getAllQuestions()
      .subscribe({   //Waits for backend response.

        next: (data) => {

          console.log(data);
          this.questions = data;  //Stores backend response.

        },

        error: (error) => {   //Logs errors if backend request fails.

          console.error(error);

        }

      });

  }

  deleteQuestion(
    id: number
  ): void {

    if (!confirm(
      'Delete this question?'
    )) {
      return;
    }

    this.questionService
      .deleteQuestion(id)
      .subscribe({

        next: () => {

          this.loadQuestions();   //After deleting, fetch questions again.

        },

        error: (error) => {

          console.error(error);

        }

      });

  }

}