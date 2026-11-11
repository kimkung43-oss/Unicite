# Software Requirements Specification (SRS)

## Unicite Integrated Learning System for Enhanced Blended Learning

**Version:** 1.0
**Date:** November 2026
**Based on:** Project Proposal, Kenyatta University

---

## 1. Introduction

### 1.1 Purpose

This document specifies the functional and non-functional requirements for the Unicite Integrated Learning System (ILS). It serves as the authoritative reference for all features the system must deliver, their priority, acceptance criteria, and current implementation status.

### 1.2 Scope

Unicite ILS is a web-based platform for enhanced blended learning. It provides course management, content delivery, communication, assessment, task management, analytics, and scheduling capabilities for students, teachers, and administrators. The backend is a Spring Boot REST API; the planned frontend is a Progressive Web App (PWA) with USSD integration for offline notifications.

### 1.3 Definitions and Acronyms

| Term | Definition |
|------|-----------|
| ILS | Integrated Learning System |
| PWA | Progressive Web Application |
| USSD | Unstructured Supplementary Service Data |
| LMS | Learning Management System |
| MoSCoW | Must / Should / Could / Won't prioritization |
| FR | Functional Requirement |
| NFR | Non-Functional Requirement |
| DTO | Data Transfer Object |

### 1.4 Stakeholders

| Stakeholder | Role |
|-------------|------|
| Students | Primary users: enroll in courses, access materials, submit assignments, collaborate |
| Teachers/Instructors | Create and manage courses, deliver content, grade, provide feedback |
| Administrators | Manage users, system configuration, reporting |
| Technical Staff | Maintain infrastructure, provide technical support |

---

## 2. System Objectives

From the thesis proposal (Chapter 1):

1. Design and assemble a PWA-based ILS with essential e-learning tools, targeting at least 90% user satisfaction.
2. Develop a communication system achieving at least 80% participation (live chat, forums).
3. Develop and integrate a comprehensive course management system for instructors.
4. Build and integrate a task management system for students.
5. Create search functionality for finding and accessing course materials.
6. Implement a notification system for course-related events and deadlines.
7. Apply a fully functional and compatible LMS across mobile phones, desktops, and tablets.
8. Implement a user-friendly interface with at least 90% user satisfaction and accessibility score.

---

## 3. Functional Requirements

### 3.1 User Management

| ID | Requirement | Priority | Acceptance Criteria | Status |
|----|------------|----------|-------------------|--------|
| FR-USR-001 | Users can register with email, password, username, first name, last name, and role | Must | POST endpoint accepts registration data, validates uniqueness of email/username, persists user, returns user profile | Implemented |
| FR-USR-002 | Users can log in with email and password | Must | POST endpoint authenticates credentials, returns session/token | Not Started |
| FR-USR-003 | Users can reset their password | Should | Password reset flow via email link | Not Started |
| FR-USR-004 | Users can view and update their profile information | Must | GET returns profile; PUT updates allowed fields | Partial (update exists, no GET profile) |
| FR-USR-005 | Administrators can manage user accounts (activate/deactivate) | Must | Admin can toggle isActive flag for any user | Partial (soft-delete exists) |
| FR-USR-006 | Users are assigned roles (Student, Instructor, Admin) | Must | Role assigned at registration, roles table seeded on startup | Implemented |
| FR-USR-007 | Passwords are hashed before storage | Must | BCrypt or equivalent hashing applied; raw passwords never stored | Not Started |
| FR-USR-008 | List all users with optional role filter | Should | GET endpoint returns users, filterable by roleId | Implemented |

### 3.2 Course Management

| ID | Requirement | Priority | Acceptance Criteria | Status |
|----|------------|----------|-------------------|--------|
| FR-CRS-001 | Instructors can create courses with name, code, description, venue | Must | POST endpoint validates uniqueness of course name, associates with instructor, persists | Implemented |
| FR-CRS-002 | Instructors can update course details | Must | PUT endpoint updates course fields | Implemented |
| FR-CRS-003 | Instructors can soft-delete (deactivate) a course | Must | Course status set to inactive, data preserved | Implemented |
| FR-CRS-004 | List courses by instructor | Should | GET endpoint returns courses filtered by userId | Schema Only (repository method exists, no controller endpoint) |
| FR-CRS-005 | Students can view available courses | Should | GET endpoint returns active courses with pagination and sorting | Schema Only (repository supports it) |
| FR-CRS-006 | Courses have topics and subtopics for content organization | Should | Topics and Subtopics entities linked to Course | Schema Only |
| FR-CRS-007 | Course search by name or code | Could | Search endpoint with query parameter | Schema Only (findByCourseCode exists) |

### 3.3 Enrollment

| ID | Requirement | Priority | Acceptance Criteria | Status |
|----|------------|----------|-------------------|--------|
| FR-ENR-001 | Students can enroll in a course | Must | POST endpoint creates enrollment record linking user to course with date | Schema Only |
| FR-ENR-002 | Students can view their enrolled courses | Must | GET endpoint returns enrollments for authenticated user | Schema Only |
| FR-ENR-003 | Instructors can view enrolled students for a course | Should | GET endpoint returns enrollments filtered by courseId | Schema Only |
| FR-ENR-004 | Students can withdraw from a course (soft-delete) | Should | Enrollment status set to inactive | Schema Only |

### 3.4 Content Delivery (Course Materials)

| ID | Requirement | Priority | Acceptance Criteria | Status |
|----|------------|----------|-------------------|--------|
| FR-MAT-001 | Instructors can upload course materials (files) | Must | Multipart POST endpoint stores file on server, creates Upload and CourseMaterial records | Partial (upload endpoint exists but uses placeholder directory) |
| FR-MAT-002 | Students can download course materials | Must | GET endpoint serves file by courseMaterialId | Not Started (method returns null) |
| FR-MAT-003 | Instructors can delete course materials | Should | DELETE endpoint removes file and database record | Not Started (method is empty) |
| FR-MAT-004 | Materials are organized by topic and subtopic | Should | CourseMaterial linked to Topic and Subtopic entities | Schema Only |
| FR-MAT-005 | Search functionality for course materials | Could | Search endpoint by title, topic, or keyword | Not Started |
| FR-MAT-006 | Integration with external library APIs (books, articles, past papers) | Could | REST client fetches from third-party education APIs, results presented alongside local materials | Not Started |

### 3.5 Communication and Collaboration

| ID | Requirement | Priority | Acceptance Criteria | Status |
|----|------------|----------|-------------------|--------|
| FR-COM-001 | Users can send direct messages to other users | Must | POST creates message in conversation, linked to sender | Schema Only |
| FR-COM-002 | Users can view conversations and message history | Must | GET returns conversations with messages | Schema Only |
| FR-COM-003 | Users can reply to messages (threaded replies) | Should | Message entity supports parent_message_id for threading | Schema Only |
| FR-COM-004 | Users can create forum threads | Must | POST creates ForumThread linked to user and course | Schema Only |
| FR-COM-005 | Users can reply to forum threads | Must | POST creates ForumReply linked to thread and user | Schema Only |
| FR-COM-006 | Message recipients tracked | Should | MessageRecipient entity tracks delivery status per user | Schema Only |
| FR-COM-007 | Announcements can be created per course | Must | POST creates Announcement with title, description, dates, linked to course | Schema Only |

### 3.6 Assessment Management

| ID | Requirement | Priority | Acceptance Criteria | Status |
|----|------------|----------|-------------------|--------|
| FR-ASM-001 | Instructors can create assignments with title, description, deadline | Must | POST creates Assignment linked to course and optionally to a group | Schema Only |
| FR-ASM-002 | Students can submit assignments (file upload) | Must | POST creates AssignmentSubmission with file path, linked to assignment and user | Schema Only |
| FR-ASM-003 | Instructors can grade submissions | Must | POST/PUT creates/updates Grade record with score for assignment + user | Schema Only |
| FR-ASM-004 | Students can view their grades | Should | GET returns grades filtered by user | Schema Only |
| FR-ASM-005 | Assignments can be assigned to course groups | Should | Assignment linked to CourseGroup via foreign key | Schema Only |
| FR-ASM-006 | Create and manage quizzes and tests | Could | Quiz entity with questions, multiple choice, true/false | Not Started |

### 3.7 Task and Note Management

| ID | Requirement | Priority | Acceptance Criteria | Status |
|----|------------|----------|-------------------|--------|
| FR-TSK-001 | Students can create personal tasks with title, description, due date | Must | POST creates Task linked to user | Schema Only |
| FR-TSK-002 | Students can mark tasks as completed | Must | PUT updates isCompleted flag | Schema Only |
| FR-TSK-003 | Students can list their tasks | Should | GET returns tasks filtered by user | Schema Only |
| FR-TSK-004 | Students can create and manage personal notes | Should | CRUD operations for Notes linked to user | Schema Only |

### 3.8 Timetable and Calendar

| ID | Requirement | Priority | Acceptance Criteria | Status |
|----|------------|----------|-------------------|--------|
| FR-TIM-001 | Instructors can create timetable entries (day, start time, end time) for a course | Must | POST creates Timetable record linked to course | Schema Only |
| FR-TIM-002 | Students can view timetable for enrolled courses | Should | GET returns timetable entries filtered by user's enrollments | Schema Only |
| FR-TIM-003 | Calendar events for deadlines and scheduled classes | Could | Calendar entity aggregating timetable + assignment deadlines | Not Started (Calendar.java is empty/commented out) |

### 3.9 Course Groups

| ID | Requirement | Priority | Acceptance Criteria | Status |
|----|------------|----------|-------------------|--------|
| FR-GRP-001 | Instructors can create groups within a course | Must | POST creates CourseGroup linked to course | Schema Only |
| FR-GRP-002 | Students can be added to groups | Must | POST creates GroupMember linking user to group | Schema Only |
| FR-GRP-003 | Group-specific assignments and submissions | Should | Assignment and AssignmentSubmission linked to CourseGroup | Schema Only |

### 3.10 Analytics and Reporting

| ID | Requirement | Priority | Acceptance Criteria | Status |
|----|------------|----------|-------------------|--------|
| FR-ANL-001 | System logs user actions with timestamps | Must | Logs entity records action, timestamp, user | Schema Only |
| FR-ANL-002 | Instructors can view student progress per course | Should | Aggregated grades and submission data per student per course | Not Started |
| FR-ANL-003 | Generate reports on attendance, grading, engagement | Should | Reporting endpoints with filtering by course, date range | Not Started |
| FR-ANL-004 | Dashboard metrics for instructors | Could | Summary statistics: enrollment count, average grade, submission rate | Not Started |

### 3.11 Notification System

| ID | Requirement | Priority | Acceptance Criteria | Status |
|----|------------|----------|-------------------|--------|
| FR-NOT-001 | Users receive notifications for assignment deadlines | Must | Notification generated when assignment deadline approaches | Not Started |
| FR-NOT-002 | Users receive notifications for new announcements | Should | Notification generated when announcement is created for enrolled course | Not Started |
| FR-NOT-003 | Users receive notifications for new messages | Should | Notification generated when message is received | Not Started |
| FR-NOT-004 | USSD-based notifications for offline users | Could | USSD gateway integration for SMS-like notifications | Not Started |

### 3.12 Badges and Leaderboards (Gamification)

| ID | Requirement | Priority | Acceptance Criteria | Status |
|----|------------|----------|-------------------|--------|
| FR-GAM-001 | Students earn badges for achievements (e.g., assignments completed, participation) | Could | Badge entity awarded based on configurable criteria | Not Started |
| FR-GAM-002 | Leaderboard ranks students by course performance | Could | Leaderboard endpoint returns ranked students by score | Not Started |

### 3.13 PWA and USSD

| ID | Requirement | Priority | Acceptance Criteria | Status |
|----|------------|----------|-------------------|--------|
| FR-PWA-001 | Frontend delivered as a Progressive Web App | Must | Service workers, manifest, offline caching | Not Started |
| FR-PWA-002 | Responsive design across mobile, tablet, desktop | Must | UI renders correctly on all screen sizes | Not Started |
| FR-PWA-003 | Offline access to downloaded course materials | Should | Materials cached locally via service worker | Not Started |
| FR-USSD-001 | USSD integration for basic interactions (notifications, grades) | Could | USSD gateway accepts dial codes, returns formatted text responses | Not Started |

---

## 4. Non-Functional Requirements

| ID | Category | Requirement | Priority | Status |
|----|----------|------------|----------|--------|
| NFR-001 | Performance | Handle at least 100 concurrent users without significant performance degradation | Must | Not Tested |
| NFR-002 | Performance | API response time under 500ms for standard CRUD operations | Must | Not Tested |
| NFR-003 | Security | All passwords hashed with BCrypt (minimum 10 rounds) | Must | Not Started |
| NFR-004 | Security | Authentication via JWT or OAuth2 tokens | Must | Not Started (Spring Security commented out) |
| NFR-005 | Security | Role-based access control (RBAC) on all endpoints | Must | Not Started |
| NFR-006 | Security | HTTPS enforced in production | Must | Not Started |
| NFR-007 | Security | Input validation on all endpoints (prevent SQL injection, XSS) | Must | Partial (Jakarta validation on some fields) |
| NFR-008 | Usability | Consistent REST API naming conventions | Should | Partial (mixed naming) |
| NFR-009 | Usability | Swagger/OpenAPI documentation for all endpoints | Should | Partial (auto-generated, config class broken) |
| NFR-010 | Accessibility | Frontend meets WCAG 2.1 AA standards | Should | Not Started (no frontend) |
| NFR-011 | Scalability | Stateless API design enabling horizontal scaling | Must | Implemented (no server-side sessions) |
| NFR-012 | Scalability | Database connection pooling (HikariCP) | Must | Implemented (Spring Boot default) |
| NFR-013 | Reliability | Automated database migrations (Flyway or Liquibase) | Should | Not Started (using ddl-auto=create) |
| NFR-014 | Reliability | Automated test suite with at least 60% code coverage | Should | Not Started (only contextLoads test) |
| NFR-015 | Maintainability | Separation of concerns: Controller, Service, Repository layers | Must | Implemented |

---

## 5. Gap Analysis Summary

| Status | Count | Percentage |
|--------|-------|------------|
| Implemented | 9 | 14% |
| Partial | 5 | 8% |
| Schema Only | 30 | 47% |
| Not Started | 20 | 31% |
| **Total** | **64** | **100%** |

### By module

| Module | Implemented | Partial | Schema Only | Not Started |
|--------|------------|---------|-------------|-------------|
| User Management | 4 | 1 | 0 | 3 |
| Course Management | 3 | 0 | 4 | 0 |
| Enrollment | 0 | 0 | 4 | 0 |
| Content Delivery | 0 | 1 | 1 | 4 |
| Communication | 0 | 0 | 7 | 0 |
| Assessment | 0 | 0 | 5 | 1 |
| Task/Notes | 0 | 0 | 4 | 0 |
| Timetable/Calendar | 0 | 0 | 2 | 1 |
| Course Groups | 0 | 0 | 3 | 0 |
| Analytics | 0 | 0 | 1 | 3 |
| Notifications | 0 | 0 | 0 | 4 |
| Gamification | 0 | 0 | 0 | 2 |
| PWA/USSD | 0 | 0 | 0 | 4 |
| Non-Functional | 2 | 3 | 0 | 10 |
