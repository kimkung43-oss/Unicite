# REST API Specification

## Unicite Integrated Learning System

**Version:** 1.0
**Base URL:** `http://localhost:8082`
**Date:** November 2026

---

## Overview

This document catalogs all REST API endpoints for the Unicite ILS, both currently implemented and planned. Each endpoint is tagged with its implementation status:

- **Implemented** -- endpoint exists and is functional
- **Partial** -- endpoint exists but has incomplete logic
- **Planned** -- endpoint does not yet exist; specification for future development

All planned endpoints assume a future API version prefix (`/api/v1/`) and JWT-based authentication. Existing endpoints retain their current paths for backward compatibility.

---

## Common Response Formats

### Success response (planned standard)

```json
{
  "status": 200,
  "message": "Operation successful",
  "data": { ... }
}
```

### Error response (planned standard)

```json
{
  "status": 400,
  "message": "Validation failed",
  "timestamp": "2026-11-11T16:00:00",
  "errors": {
    "email": "Enter a valid email address",
    "password": "must not be blank"
  }
}
```

### Current error response (validation)

```json
{
  "email": "Enter a valid email address",
  "password": "must not be blank"
}
```

---

## 1. Authentication

> Status: **Planned** (Spring Security is currently disabled)

| Method | Path | Description | Auth |
|--------|------|-------------|------|
| POST | `/api/v1/auth/register` | Register a new user | None |
| POST | `/api/v1/auth/login` | Authenticate and receive JWT | None |
| POST | `/api/v1/auth/refresh` | Refresh an expired JWT | Bearer token |
| POST | `/api/v1/auth/logout` | Invalidate current token | Bearer token |
| POST | `/api/v1/auth/forgot-password` | Request password reset email | None |
| POST | `/api/v1/auth/reset-password` | Reset password with token | None |

### POST `/api/v1/auth/login`

**Request body:**
```json
{
  "email": "instructor@unicite.co.ke",
  "password": "securepassword"
}
```

**Response (200):**
```json
{
  "token": "eyJhbGciOi...",
  "tokenType": "Bearer",
  "expiresIn": 86400,
  "user": {
    "id": 1,
    "email": "instructor@unicite.co.ke",
    "username": "instructor1",
    "firstName": "Jane",
    "lastName": "Doe",
    "roles": ["INSTRUCTOR"]
  }
}
```

---

## 2. User Management

### Implemented endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| POST | `/user/signup` | Create a new user | None | **Implemented** |
| PUT | `/user/update` | Update user details | None | **Implemented** |
| POST | `/user/delete?userId={id}` | Soft-delete a user | None | **Implemented** |
| GET | `/user/list/all` | List all users | None | **Implemented** |
| GET | `/user/list/roles/?roleId={id}` | List users by role | None | **Implemented** |

### POST `/user/signup` -- Implemented

**Request body:**
```json
{
  "email": "student@unicite.co.ke",
  "password": "password123",
  "username": "student1",
  "firstName": "John",
  "lastName": "Doe",
  "roleCode": "learner"
}
```

**Response (200):**
```json
{
  "id": 1,
  "email": "student@unicite.co.ke",
  "password": "password123",
  "username": "student1",
  "firstName": "John",
  "lastName": "Doe",
  "isActive": true,
  "roles": [
    { "roleId": 2, "roleName": "Learner", "code": "learner" }
  ],
  "courses": []
}
```

**Error responses:**
- `400` -- `"Email already exists."`
- `400` -- `"Username is taken. Please enter a new one"`
- `400` -- Validation errors map (if `@Valid` fields fail)

### PUT `/user/update` -- Implemented

**Request body:**
```json
{
  "userId": 1,
  "firstName": "John",
  "lastName": "Smith",
  "username": "john_smith",
  "password": "newpassword"
}
```

**Response (200):** `"User updated succesfully"`

### Planned endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET | `/api/v1/users/{id}` | Get user by ID | Bearer | **Planned** |
| GET | `/api/v1/users/me` | Get current authenticated user profile | Bearer | **Planned** |
| PUT | `/api/v1/users/me` | Update own profile | Bearer | **Planned** |
| GET | `/api/v1/users` | List users (paginated, filterable) | Admin | **Planned** |

---

## 3. Role Management

### Implemented endpoints (Spring Data REST auto-exposed)

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET | `/roleses` | List all roles | None | **Implemented** |
| POST | `/roleses` | Create a new role | None | **Implemented** |
| GET | `/roleses/{id}` | Get role by ID | None | **Implemented** |
| PUT | `/roleses/{id}` | Update a role | None | **Implemented** |
| DELETE | `/roleses/{id}` | Delete a role | None | **Implemented** |

### POST `/roleses` -- Implemented

**Request body:**
```json
{
  "roleName": "Instructor",
  "code": "instructor"
}
```

**Response (201):**
```json
{
  "roleName": "Instructor",
  "code": "instructor",
  "_links": {
    "self": { "href": "http://localhost:8082/roleses/1" },
    "roles": { "href": "http://localhost:8082/roleses/1" }
  }
}
```

---

## 4. Course Management

### Implemented endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| POST | `/courses/create` | Create a new course | None | **Implemented** |
| PUT | `/courses/update` | Update a course | None | **Implemented** |
| POST | `/courses/delete?courseId={id}` | Soft-delete a course | None | **Implemented** |

### POST `/courses/create` -- Implemented

**Request body:**
```json
{
  "courseName": "Introduction to Computer Science",
  "courseCode": "CS101",
  "description": "Fundamentals of computing",
  "venueName": "Room A1",
  "userId": 1
}
```

**Response (200):**
```json
{
  "courseId": 1,
  "courseName": "Introduction to Computer Science",
  "courseCode": "CS101",
  "description": "Fundamentals of computing",
  "venueName": "Room A1",
  "user": { ... },
  "isActive": true
}
```

**Error responses:**
- `400` -- `"Please enter the name of this course."`
- `400` -- `"Course already exists. Please enter another course name"`

### Planned endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET | `/api/v1/courses` | List all active courses (paginated) | Bearer | **Planned** |
| GET | `/api/v1/courses/{id}` | Get course by ID | Bearer | **Planned** |
| GET | `/api/v1/courses/instructor/{userId}` | List courses by instructor | Bearer | **Planned** |
| GET | `/api/v1/courses/search?q={query}` | Search courses by name/code | Bearer | **Planned** |

---

## 5. Enrollment

### Implemented endpoints (Spring Data REST auto-exposed)

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET | `/enrollmentses` | List all enrollments | None | **Implemented** (auto) |
| POST | `/enrollmentses` | Create enrollment | None | **Implemented** (auto) |

### Planned endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| POST | `/api/v1/enrollments` | Enroll student in course | Bearer (Student) | **Planned** |
| GET | `/api/v1/enrollments/me` | Get current user's enrollments | Bearer | **Planned** |
| GET | `/api/v1/enrollments/course/{courseId}` | List enrollments for a course | Bearer (Instructor) | **Planned** |
| DELETE | `/api/v1/enrollments/{id}` | Withdraw from course (soft-delete) | Bearer (Student) | **Planned** |

### POST `/api/v1/enrollments` -- Planned

**Request body:**
```json
{
  "courseId": 1
}
```

**Response (201):**
```json
{
  "enrollmentId": 1,
  "isActive": true,
  "enrolledDate": "2026-11-11",
  "userId": 2,
  "courseId": 1
}
```

---

## 6. Course Materials

### Implemented endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| POST | `/courses/course materials/upload` | Upload course material | None | **Partial** (placeholder upload dir) |

### Planned endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| POST | `/api/v1/courses/{courseId}/materials` | Upload material to a course | Bearer (Instructor) | **Planned** |
| GET | `/api/v1/courses/{courseId}/materials` | List materials for a course | Bearer | **Planned** |
| GET | `/api/v1/materials/{id}/download` | Download a material file | Bearer | **Planned** |
| DELETE | `/api/v1/materials/{id}` | Delete a material | Bearer (Instructor) | **Planned** |
| GET | `/api/v1/materials/search?q={query}` | Search materials | Bearer | **Planned** |

---

## 7. Topics and Subtopics

### Implemented endpoints (Spring Data REST auto-exposed)

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET/POST/PUT/DELETE | `/topicses/**` | CRUD for topics | None | **Implemented** (auto) |
| GET/POST/PUT/DELETE | `/subtopicses/**` | CRUD for subtopics | None | **Implemented** (auto) |

### Planned endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET | `/api/v1/courses/{courseId}/topics` | List topics for a course | Bearer | **Planned** |
| POST | `/api/v1/courses/{courseId}/topics` | Create topic in course | Bearer (Instructor) | **Planned** |
| GET | `/api/v1/topics/{topicId}/subtopics` | List subtopics | Bearer | **Planned** |
| POST | `/api/v1/topics/{topicId}/subtopics` | Create subtopic | Bearer (Instructor) | **Planned** |

---

## 8. Assignments

### Implemented endpoints (Spring Data REST auto-exposed)

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET/POST/PUT/DELETE | `/assignmentses/**` | CRUD for assignments | None | **Implemented** (auto) |
| GET/POST/PUT/DELETE | `/assignmentSubmissions/**` | CRUD for submissions | None | **Implemented** (auto) |

### Planned endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| POST | `/api/v1/courses/{courseId}/assignments` | Create assignment | Bearer (Instructor) | **Planned** |
| GET | `/api/v1/courses/{courseId}/assignments` | List assignments for course | Bearer | **Planned** |
| GET | `/api/v1/assignments/{id}` | Get assignment details | Bearer | **Planned** |
| POST | `/api/v1/assignments/{id}/submit` | Submit assignment (file) | Bearer (Student) | **Planned** |
| GET | `/api/v1/assignments/{id}/submissions` | List submissions | Bearer (Instructor) | **Planned** |

---

## 9. Grades

### Implemented endpoints (Spring Data REST auto-exposed)

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET/POST/PUT/DELETE | `/gradeses/**` | CRUD for grades | None | **Implemented** (auto) |

### Planned endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| POST | `/api/v1/grades` | Grade a submission | Bearer (Instructor) | **Planned** |
| GET | `/api/v1/grades/me` | Get current user's grades | Bearer (Student) | **Planned** |
| GET | `/api/v1/grades/course/{courseId}` | Get all grades for a course | Bearer (Instructor) | **Planned** |
| GET | `/api/v1/grades/student/{userId}/course/{courseId}` | Get student's grades for a course | Bearer | **Planned** |

---

## 10. Communication -- Messaging

### Implemented endpoints (Spring Data REST auto-exposed)

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET/POST | `/conversations/**` | CRUD for conversations | None | **Implemented** (auto) |
| GET/POST | `/messageses/**` | CRUD for messages | None | **Implemented** (auto) |

### Planned endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| POST | `/api/v1/conversations` | Start a new conversation | Bearer | **Planned** |
| GET | `/api/v1/conversations` | List user's conversations | Bearer | **Planned** |
| GET | `/api/v1/conversations/{id}/messages` | Get messages in conversation | Bearer | **Planned** |
| POST | `/api/v1/conversations/{id}/messages` | Send a message | Bearer | **Planned** |

---

## 11. Communication -- Forums

### Implemented endpoints (Spring Data REST auto-exposed)

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET/POST | `/forumThreadses/**` | CRUD for forum threads | None | **Implemented** (auto) |
| GET/POST | `/forumReplies/**` | CRUD for forum replies | None | **Implemented** (auto) |

### Planned endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET | `/api/v1/forums/threads` | List forum threads (paginated) | Bearer | **Planned** |
| POST | `/api/v1/forums/threads` | Create a new thread | Bearer | **Planned** |
| GET | `/api/v1/forums/threads/{id}` | Get thread with replies | Bearer | **Planned** |
| POST | `/api/v1/forums/threads/{id}/replies` | Reply to a thread | Bearer | **Planned** |

---

## 12. Announcements

### Implemented endpoints (Spring Data REST auto-exposed)

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET/POST/PUT/DELETE | `/announcementses/**` | CRUD for announcements | None | **Implemented** (auto) |

### Planned endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| POST | `/api/v1/courses/{courseId}/announcements` | Create announcement | Bearer (Instructor) | **Planned** |
| GET | `/api/v1/courses/{courseId}/announcements` | List announcements for course | Bearer | **Planned** |
| GET | `/api/v1/announcements/me` | List announcements for enrolled courses | Bearer (Student) | **Planned** |

---

## 13. Course Groups

### Implemented endpoints (Spring Data REST auto-exposed)

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET/POST/PUT/DELETE | `/courseGroupses/**` | CRUD for groups | None | **Implemented** (auto) |

### Planned endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| POST | `/api/v1/courses/{courseId}/groups` | Create group in course | Bearer (Instructor) | **Planned** |
| GET | `/api/v1/courses/{courseId}/groups` | List groups in course | Bearer | **Planned** |
| POST | `/api/v1/groups/{groupId}/members` | Add member to group | Bearer (Instructor) | **Planned** |
| DELETE | `/api/v1/groups/{groupId}/members/{userId}` | Remove member | Bearer (Instructor) | **Planned** |

---

## 14. Timetable

### Implemented endpoints (Spring Data REST auto-exposed)

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET/POST/PUT/DELETE | `/timetables/**` | CRUD for timetable entries | None | **Implemented** (auto) |

### Planned endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| POST | `/api/v1/courses/{courseId}/timetable` | Create timetable entry | Bearer (Instructor) | **Planned** |
| GET | `/api/v1/courses/{courseId}/timetable` | Get timetable for course | Bearer | **Planned** |
| GET | `/api/v1/timetable/me` | Get timetable for enrolled courses | Bearer (Student) | **Planned** |

---

## 15. Tasks and Notes

### Implemented endpoints (Spring Data REST auto-exposed)

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET/POST/PUT/DELETE | `/taskses/**` | CRUD for tasks | None | **Implemented** (auto) |
| GET/POST/PUT/DELETE | `/noteses/**` | CRUD for notes | None | **Implemented** (auto) |

### Planned endpoints

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| POST | `/api/v1/tasks` | Create a personal task | Bearer | **Planned** |
| GET | `/api/v1/tasks` | List current user's tasks | Bearer | **Planned** |
| PUT | `/api/v1/tasks/{id}` | Update task (mark complete) | Bearer | **Planned** |
| DELETE | `/api/v1/tasks/{id}` | Delete a task | Bearer | **Planned** |
| POST | `/api/v1/notes` | Create a note | Bearer | **Planned** |
| GET | `/api/v1/notes` | List current user's notes | Bearer | **Planned** |
| PUT | `/api/v1/notes/{id}` | Update a note | Bearer | **Planned** |
| DELETE | `/api/v1/notes/{id}` | Delete a note | Bearer | **Planned** |

---

## 16. Analytics and Reporting

> Status: **Planned** (Logs entity exists but no analytics endpoints)

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET | `/api/v1/analytics/courses/{courseId}/summary` | Course summary stats | Bearer (Instructor) | **Planned** |
| GET | `/api/v1/analytics/courses/{courseId}/grades` | Grade distribution | Bearer (Instructor) | **Planned** |
| GET | `/api/v1/analytics/courses/{courseId}/engagement` | Student engagement metrics | Bearer (Instructor) | **Planned** |
| GET | `/api/v1/analytics/students/{userId}/progress` | Student progress report | Bearer | **Planned** |
| GET | `/api/v1/analytics/dashboard` | Admin dashboard metrics | Bearer (Admin) | **Planned** |

---

## 17. Notifications

> Status: **Planned**

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET | `/api/v1/notifications` | List current user's notifications | Bearer | **Planned** |
| PUT | `/api/v1/notifications/{id}/read` | Mark notification as read | Bearer | **Planned** |
| PUT | `/api/v1/notifications/read-all` | Mark all as read | Bearer | **Planned** |

---

## 18. Gamification (Badges and Leaderboards)

> Status: **Planned**

| Method | Path | Description | Auth | Status |
|--------|------|-------------|------|--------|
| GET | `/api/v1/badges/me` | List current user's badges | Bearer | **Planned** |
| GET | `/api/v1/courses/{courseId}/leaderboard` | Course leaderboard | Bearer | **Planned** |

---

## Endpoint Summary

| Category | Implemented | Partial | Planned | Total |
|----------|------------|---------|---------|-------|
| Authentication | 0 | 0 | 6 | 6 |
| User Management | 5 | 0 | 4 | 9 |
| Role Management | 5 | 0 | 0 | 5 |
| Course Management | 3 | 0 | 4 | 7 |
| Enrollment | 2 (auto) | 0 | 4 | 6 |
| Course Materials | 0 | 1 | 5 | 6 |
| Topics/Subtopics | 4 (auto) | 0 | 4 | 8 |
| Assignments | 4 (auto) | 0 | 5 | 9 |
| Grades | 4 (auto) | 0 | 4 | 8 |
| Messaging | 4 (auto) | 0 | 4 | 8 |
| Forums | 4 (auto) | 0 | 4 | 8 |
| Announcements | 4 (auto) | 0 | 3 | 7 |
| Course Groups | 4 (auto) | 0 | 4 | 8 |
| Timetable | 4 (auto) | 0 | 3 | 7 |
| Tasks/Notes | 8 (auto) | 0 | 8 | 16 |
| Analytics | 0 | 0 | 5 | 5 |
| Notifications | 0 | 0 | 3 | 3 |
| Gamification | 0 | 0 | 2 | 2 |
| **Total** | **55** | **1** | **72** | **128** |
