# 🎓 Online Exam Management System - Backend

A comprehensive **Spring Boot-based Online Exam Management System** that manages the complete lifecycle of online examinations, from question creation to result generation, analytics, and leaderboards.

---

# 📌 Project Overview

The **Online Exam Management System** provides a complete backend solution for conducting and managing online examinations.

The application supports:

- User Management
- Authentication
- Question Bank Management
- Exam Management
- Exam Attempts
- Answer Submission & Auto Save
- Exam Timer & Auto Submission
- Automated Evaluation
- Result Management
- Candidate Dashboard
- Exam Statistics
- Leaderboards

---

# 🚀 Technology Stack

## Backend

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- Maven
- Lombok
- Jakarta Validation

## Database

- H2 Database (Development)

## API Testing

- Postman

---

# 📂 Project Structure

```text
com.examportal

├── controller
├── dto
├── entity
├── enums
├── exception
├── repository
├── service
└── OnlineExamManagementSystemApplication
```

---

# 🗄️ Entity Model

## User

Represents system users.

```text
id
firstName
lastName
email
password
role
```

### Roles

```text
ADMIN
CANDIDATE
```

---

## Question

Stores question bank information.

```text
id
questionText
optionA
optionB
optionC
optionD
correctAnswer
difficulty
category
```

---

## Exam

Represents an examination.

```text
id
title
description
durationInMinutes
totalMarks
passingMarks
questions
```

### Relationship

```text
Exam
  ↔
Question

Many-To-Many
```

---

## ExamAttempt

Represents a candidate's exam session.

```text
id
exam
user
startTime
endTime
score
status
```

### Attempt Status

```text
IN_PROGRESS
SUBMITTED
EVALUATED
```

---

## Answer

Stores candidate responses.

```text
id
selectedAnswer
correct
marksAwarded
examAttempt
question
```

---

## Result

Stores evaluated examination results.

```text
id
examAttempt
totalQuestions
correctAnswers
score
percentage
resultStatus
evaluatedAt
```

---

# 🔗 Entity Relationships

```text
User
  │
  └── One-To-Many
          │
          ▼
     ExamAttempt
          │
          ├── One-To-One ──► Result
          │
          └── One-To-Many ──► Answer

Exam
  │
  ├── One-To-Many ──► ExamAttempt
  │
  └── Many-To-Many ──► Question

Question
  │
  └── One-To-Many ──► Answer
```

---

# 📦 Modules

## 1. Authentication Module

### Features

- User Registration
- User Login
- Role Management

---

## 2. Question Management Module

### Features

- Create Question
- Update Question
- Delete Question
- Get Question By Id
- Get All Questions

---

## 3. Exam Management Module

### Features

- Create Exam
- Update Exam
- Delete Exam
- Get Exam By Id
- Get All Exams

---

## 4. Exam Builder Module

### Features

- Assign Questions To Exam
- Remove Questions From Exam
- Get Exam Questions

---

## 5. Exam Attempt Module

### Features

- Start Exam
- Submit Exam
- Get Attempt Details
- Get User Attempts

### Business Rules

✅ Prevent duplicate exam attempts

✅ Track exam lifecycle

```text
IN_PROGRESS
     ↓
SUBMITTED
     ↓
EVALUATED
```

---

## 6. Answer Module

### Features

- Submit Answer
- Update Answer
- Get Answers By Attempt

### Auto Save Support

```text
Candidate Selects Answer
            ↓
Answer Saved

Candidate Changes Answer
            ↓
Existing Answer Updated
```

---

## 7. Exam Timer Module

### Features

- Remaining Time Calculation
- Expiration Check
- Automatic Submission
- Answer Submission Restriction

### Timeout Workflow

```text
Exam Starts
     ↓
Timer Begins
     ↓
Exam Duration Expires
     ↓
Attempt Automatically Submitted
     ↓
Additional Answers Blocked
```

---

## 8. Evaluation Module

### Features

- Answer Evaluation
- Score Calculation
- Percentage Calculation
- Pass/Fail Determination
- Re-Evaluation Prevention

### Evaluation Rules

Each answer is compared against the question's correct answer.

```text
Correct Answer
      ↓
Score +1

Incorrect Answer
      ↓
Score +0
```

### Passing Criteria

Configured per exam using:

```text
Exam.passingMarks
```

Passing marks are treated as the minimum passing percentage.

---

## 9. Result Module

### Features

- Persist Results
- Fetch Result By ID
- Fetch Result By Attempt
- Result History
- Pagination Support

---

## 10. Analytics Module

### Exam Statistics

Provides:

```text
Total Attempts
Passed Candidates
Failed Candidates
Pass Percentage
Average Score
```

---

### Candidate Dashboard

Provides:

```text
Total Exams Attempted
Passed Exams
Failed Exams
Average Percentage
```

---

### Leaderboard

Provides:

```text
Exam-Specific Rankings
Tie-Aware Ranking
```

Example:

```text
100% → Rank 1
95%  → Rank 2
95%  → Rank 2
90%  → Rank 4
```

---

# 🔄 Complete System Workflow

---

## Admin Workflow

```text
Create Questions
        ↓
Create Exams
        ↓
Assign Questions To Exams
        ↓
Publish Exam
```

---

## Candidate Workflow

```text
Register
    ↓
Login
    ↓
View Exams
    ↓
Start Exam
    ↓
Answer Questions
    ↓
Timer Running
    ↓
Submit Exam
    ↓
Evaluation
    ↓
Result Generation
    ↓
View Result
    ↓
Leaderboard
```

---

# 📝 Exam Execution Workflow

```text
Start Exam
    ↓
Submit Answers
    ↓
Submit Exam
    ↓
Evaluate Exam
    ↓
Store Result In RESULTS Table
    ↓
Return ResultResponse
```

---

# ⏰ Timer Workflow

```text
Start Exam
      ↓
Timer Started
      ↓
Remaining Time API
      ↓
Duration Expired?
      ↓
      ├── NO
      │      ↓
      │  Continue Exam
      │
      └── YES
             ↓
      Auto Submit Exam
             ↓
      Block New Answers
             ↓
      Proceed For Evaluation
```

---

# 📊 Statistics Workflow

```text
Exam
   ↓
Fetch Results
   ↓
Calculate Metrics
   ↓
Return Statistics Response
```

Metrics:

```text
Total Attempts
Passed Candidates
Failed Candidates
Pass Percentage
Average Score
```

---

# 📈 Dashboard Workflow

```text
User
   ↓
Fetch Attempts
   ↓
Fetch Results
   ↓
Calculate Dashboard Metrics
   ↓
Return Dashboard Response
```

---

# 🏆 Leaderboard Workflow

```text
Exam
   ↓
Fetch Results
   ↓
Sort By Percentage
   ↓
Resolve Ranking Ties
   ↓
Generate Rankings
   ↓
Return Leaderboard
```

---

# ⚠️ Custom Exceptions

The system includes centralized exception handling.

Implemented exceptions include:

```text
ExamNotFoundException

QuestionNotFoundException

ExamAttemptNotFoundException

QuestionNotAssignedException

ExamAlreadyEvaluatedException

ResultNotFoundException
```

---

# ✅ Validation

Input validation is implemented using Jakarta Validation.

Examples:

```java
@NotNull
@NotBlank
@Pattern
```

Used across:

- Authentication DTOs
- Exam DTOs
- Question DTOs
- Answer DTOs
- Attempt DTOs

---

# 📚 REST APIs Summary

## Authentication

```http
POST /api/auth/register
POST /api/auth/login
```

---

## Questions

```http
POST   /api/questions
GET    /api/questions
GET    /api/questions/{id}
PUT    /api/questions/{id}
DELETE /api/questions/{id}
```

---

## Exams

```http
POST   /api/exams
GET    /api/exams
GET    /api/exams/{id}
PUT    /api/exams/{id}
DELETE /api/exams/{id}
```

---

## Exam Builder

```http
POST   /api/exams/{examId}/questions/{questionId}
DELETE /api/exams/{examId}/questions/{questionId}
GET    /api/exams/{examId}/questions
```

---

## Exam Attempts

```http
POST /api/exam-attempts/start
POST /api/exam-attempts/{attemptId}/submit

GET  /api/exam-attempts/{attemptId}
GET  /api/exam-attempts/user/{userId}

GET  /api/exam-attempts/{attemptId}/remaining-time
```

---

## Answers

```http
POST /api/answers

GET  /api/answers/{answerId}
GET  /api/answers/attempt/{attemptId}
```

---

## Evaluation

```http
POST /api/evaluation/{attemptId}
```

---

## Results

```http
GET /api/results/{resultId}

GET /api/results/attempt/{attemptId}

GET /api/results/user/{userId}
```

---

## Statistics

```http
GET /api/statistics/exams/{examId}
```

---

## Dashboard

```http
GET /api/dashboard/user/{userId}
```

---

## Leaderboard

```http
GET /api/leaderboard/exam/{examId}
```

---

# ✅ Current Features

```text
✅ Authentication
✅ Question Management
✅ Exam Management
✅ Exam Builder
✅ Exam Attempts
✅ Answer Submission
✅ Auto Save Answers
✅ Exam Timer
✅ Auto Submit
✅ Remaining Time API
✅ Evaluation
✅ Result Persistence
✅ Result History
✅ Pagination
✅ Candidate Dashboard
✅ Exam Statistics
✅ Leaderboard
✅ Tie-Aware Ranking
✅ Global Exception Handling
✅ Validation
```

---

# 🔮 Future Enhancements

```text
JWT Authentication

Role-Based Authorization

Swagger / OpenAPI Documentation

Docker Support

Unit Testing

Integration Testing

Angular Frontend

Advanced Dashboard Analytics

Email Notifications

Exam Certificates
```

---

# 🎯 Current Status

The backend currently supports a complete end-to-end online examination workflow:

```text
Question Creation
        ↓
Exam Creation
        ↓
Question Assignment
        ↓
Exam Attempt
        ↓
Answer Submission
        ↓
Automatic Timer Handling
        ↓
Exam Evaluation
        ↓
Result Generation
        ↓
Dashboard & Analytics
        ↓
Leaderboard
```

---
## ✅ Ready For Frontend Integration

The backend is now fully capable of supporting an Angular frontend for:

- Admin Portal
- Candidate Portal
- Exam Interface
- Result Dashboard
- Analytics Dashboard
- Leaderboards

