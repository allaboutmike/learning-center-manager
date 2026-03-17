# Dallas Learning Center

Dallas Learning Center is a full-stack web application built during a 6-week software engineering cohort. It is designed to improve how tutoring centers manage sessions, track student progress, and retain customers.

---

## Live Demo

- **Application:** https://capable-blini-676354.netlify.app/  
- **API Documentation (Swagger):** https://learning-center-manager-production.up.railway.app/swagger-ui/index.html#/

---
## Table of Contents
1. [Overview](#overview)  
2. [Problem Statement](#problem-statement)  
3. [Solution](#solution)  
4. [Features](#features)  
5. [Technical Details](#technical-details)  
6. [API Overview](#api-overview)  
7. [Getting Started](#getting-started)  
8. [Contributing](#contributing)  
9. [Project Team](#project-team)  

---

## Overview

### Goal
Improve tutoring center operations by making session tracking, progress visibility, and booking more reliable and transparent.

### Audience
- Parents  
- Tutors  
- Administrators  
- New users exploring the platform  

---

## Problem Statement

Private tutoring centers face a few common challenges:

- Parents cannot clearly see their child’s progress, which leads to customer churn  
- Sessions are not always properly tracked, causing revenue leakage  
- Tutors lack context from previous sessions, which affects continuity  
- There is no clear way to set and track learning goals  
- New users cannot easily explore services before committing  

---

## Solution

Dallas Learning Center addresses these issues by providing:

- A progress dashboard so parents can clearly see improvement over time  
- A credit-based system that tracks every session and prevents missed billing  
- Session notes so tutors can continue where others left off  
- Goal tracking to measure student progress in a structured way  
- A simple booking experience for scheduling sessions  
- A logged-out experience that allows users to explore tutors and availability before signing up  

---

## Features

- Browse tutors and view availability before booking  
- Book sessions for students in a few simple steps  
- Logged-out experience that allows users to explore the platform before signing up  
- Purchase and manage session credits  
- Automatic credit deduction after each session  
- Track student progress using visual dashboards
- View subject-level performance and trends  
- Set and monitor learning goals  
- Store and review session history  
- Tutors can add session notes after each session  
- Maintain continuity across sessions using tutor notes  
- Manage multiple students under one account
- Track and store all sessions in the system  
- Ensure accurate session tracking and billing

  <img width="2514" height="1392" alt="image" src="https://github.com/user-attachments/assets/52149379-6913-4919-b5d7-3afaef5540cf" />
  <img width="1048" height="954" alt="image" src="https://github.com/user-attachments/assets/1346093a-c638-4562-8e79-0fd8cc74149c" />
  <img width="2500" height="1429" alt="image" src="https://github.com/user-attachments/assets/586b8eaa-46a8-484e-93a2-68ba2666fdcb" />
  <img width="2548" height="1423" alt="image" src="https://github.com/user-attachments/assets/1dbab98c-1ff3-4583-beb0-0f8cab6dcf98" />
  <img width="1173" height="704" alt="image" src="https://github.com/user-attachments/assets/2201dcf2-4b9f-4112-a389-b84d10c04daf" />
  <img width="2545" height="1427" alt="image" src="https://github.com/user-attachments/assets/646a05f4-623c-4079-8519-f8ab2792fdaa" />
  <img width="2526" height="1435" alt="image" src="https://github.com/user-attachments/assets/69711939-16b1-4943-8469-cd39c23b3075" />
  <img width="2526" height="1417" alt="image" src="https://github.com/user-attachments/assets/4738634b-bfcb-4330-8293-5d33c5316a63" />
  <img width="942" height="304" alt="image" src="https://github.com/user-attachments/assets/ed63228e-909a-45e3-8e32-ecb6d3c87347" />
  <img width="751" height="689" alt="image" src="https://github.com/user-attachments/assets/d366509f-2b9e-43ac-9f71-6c416851f109" />
  <img width="1016" height="969" alt="image" src="https://github.com/user-attachments/assets/a837b383-8d43-4e69-9ccb-6354cd2cd23e" />



---

## Technical Details

**Frontend**
- Built with React + Vite  
- Deployed on Netlify  
- Production: https://capable-blini-676354.netlify.app/  

**Backend**
- Java + Spring Boot  
- Hosted on Railway  

**Database**
- H2 (development)  
- PostgreSQL (production)  
- Flyway for migrations  

---

## API Overview

The backend exposes RESTful APIs for managing tutors, parents, students, sessions, and progress tracking.

Swagger UI:  
https://learning-center-manager-production.up.railway.app/swagger-ui/index.html#/

Key capabilities:
- Session management  
- Parent and student management  
- Credit tracking  
- Progress tracking  
- Tutor availability and notes  

---

## Getting Started

Clone the repository and navigate into the project:

```bash
git clone https://github.com/allaboutmike/learning-center-manager.git
cd learning-center-manager


## Running the Project Locally

### Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

Frontend runs on:  
http://localhost:5173  

---

### Backend Setup

```bash
cd ../backend
./mvnw clean install
./mvnw spring-boot:run
```

Backend runs on:  
http://localhost:8080  

---

### Environment Configuration

Create a `.env` file in the frontend if needed:

```plaintext
VITE_API_BASE_URL=http://localhost:8080
```

---

### Connect Frontend and Backend

Make sure the frontend is calling:  
http://localhost:8080  

---

---

## Contributing

```bash
git checkout -b feature/your-feature-name
git commit -m "Add your feature"
git push origin feature/your-feature-name
```

Open a pull request after pushing your changes.

---

## Project Team

- Brandon  
- Ermoni  
- Justin  
- Nikhil  
- Yodit (https://www.linkedin.com/in/yodit-weldegeorgise/)

This project was built collaboratively by a team of 5 developers and 1 team lead over 6 weeks.  
