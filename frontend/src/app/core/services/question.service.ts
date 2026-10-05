import { Injectable } from '@angular/core';

import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';

import { Question } from '../models/question.model';

import { CreateQuestion } from '../models/create-question.model';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  private apiUrl =
    'http://localhost:8080/api/questions';

  constructor(
    private http: HttpClient
  ) { }

  getAllQuestions(): Observable<Question[]> {

    return this.http.get<Question[]>(
      this.apiUrl
    );

  }

  deleteQuestion(
    id: number
  ): Observable<void> {

    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    );

  }

  createQuestion(
    question: CreateQuestion
  ) {

    return this.http.post(
      this.apiUrl,
      question
    );

  }

}