# System Design Document

## Unicite Integrated Learning System

**Version:** 1.0
**Date:** November 2026

---

## 1. Architecture Overview

Unicite follows a **three-tier architecture** separating presentation, business logic, and data storage:

```
┌─────────────────────────────────────────────────────┐
│                 Presentation Layer                   │
│  PWA Frontend (Ionic/HTML/CSS/JS) + Swagger UI      │
│  USSD Gateway (planned)                             │
└──────────────────────┬──────────────────────────────┘
                       │ HTTP/REST (JSON)
┌──────────────────────▼──────────────────────────────┐
│               Business Logic Layer                   │
│  Spring Boot 3.1.1 REST API (Port 8082)             │
│  ┌────────────┐ ┌──────────┐ ┌───────────────────┐  │
│  │ Controllers│→│ Services │→│   Repositories    │  │
│  └────────────┘ └──────────┘ └───────────────────┘  │
│  Spring Data REST (auto-exposed JPA repos)          │
│  SpringDoc OpenAPI (Swagger UI)                     │
└──────────────────────┬──────────────────────────────┘
                       │ JDBC (HikariCP)
┌──────────────────────▼──────────────────────────────┐
│                Data Storage Layer                    │
│  PostgreSQL 12+ (localhost:5432, database: unicite) │
│  File System (upload directory for course materials)│
└─────────────────────────────────────────────────────┘
```

---

## 2. Technology Stack

### Current (Implemented)

| Component | Technology | Version |
|-----------|-----------|---------|
| Language | Java | 20 |
| Framework | Spring Boot | 3.1.1 |
| ORM | Hibernate (via Spring Data JPA) | 6.2.5.Final |
| Database | PostgreSQL | 12+ |
| Connection Pool | HikariCP | (Spring Boot default) |
| Build Tool | Maven | 3.9.2 (via wrapper) |
| API Documentation | SpringDoc OpenAPI | 2.1.0 |
| Code Generation | Lombok | (Spring Boot managed) |
| Validation | Jakarta Bean Validation | (Spring Boot Starter) |
| Serialization | Jackson | (Spring Boot managed) |

### Planned (Not Yet Implemented)

| Component | Technology | Notes |
|-----------|-----------|-------|
| Frontend | Ionic + HTML/CSS/JS (PWA) | Thesis specifies Ionic framework |
| Authentication | Spring Security + OAuth2 | Dependencies in pom.xml, commented out |
| Monitoring | Spring Boot Actuator | Dependency in pom.xml, commented out |
| USSD Gateway | Third-party USSD API | For offline notifications |
| External APIs | REST clients for library APIs | Books, articles, past papers |
| CI/CD | GitHub Actions | Currently using GitHub for VCS only |
| Database Migrations | Flyway or Liquibase | Currently using ddl-auto=create |

---

## 3. Package Structure and Module Decomposition

### Current package layout

```
co.ke.unicite
├── UniciteApplication.java              # Main class + role seeding
├── Config/
│   └── swaggerApiConfig.java            # OpenAPI bean (missing @Configuration)
├── Controllers/
│   ├── UserController.java              # /user/** endpoints
│   ├── CourseController.java            # /courses/** endpoints
│   └── CourseMaterialsController.java   # /courses/course materials/** endpoints
├── Services/
│   ├── UsersService.java                # Interface
│   ├── UserServiceImpl.java             # Implementation
│   ├── CourseService.java               # Interface
│   ├── CourseServiceImpl.java           # Implementation
│   ├── CourseMaterialsService.java      # Interface
│   └── CourseMaterialsServiceImpl.java  # Implementation
├── Repositories/
│   ├── UsersRepository.java
│   ├── CoursesRepository.java
│   ├── RoleRepository.java
│   ├── CourseMaterialsRepository.java
│   ├── UploadsRepository.java
│   ├── AssignmentsRepository.java
│   ├── AssignmentsSubmissionRepository.java
│   ├── GradesRepository.java
│   ├── EnrollmentsRepository.java
│   ├── CourseGroupsRepository.java
│   ├── CourseGroupMembersRepository.java
│   ├── TopicsRepository.java
│   ├── SubtopicsRepository.java
│   ├── ConversationRepository.java
│   ├── MessagesRepository.java
│   ├── MessageRecipientRepository.java
│   ├── ForumThreadsRepository.java
│   ├── ForumReplyRepository.java
│   ├── AnnouncementsRepository.java
│   ├── TimetableRepository.java
│   ├── TasksRepository.java
│   ├── NotesRepository.java
│   └── LogsRepository.java
├── Models/                              # DTOs for request/response
│   ├── UsersModel.java
│   ├── CoursesModel.java
│   ├── CourseMaterialsModel.java
│   ├── UploadsModel.java
│   ├── AssignmentsModel.java
│   ├── AssignmentSubmissionModel.java
│   ├── GradesModel.java
│   ├── EnrollmentsModel.java
│   ├── CourseGroupsModel.java
│   ├── CourseGroupMembersModel.java
│   ├── TopicsModel.java
│   ├── SubtopicsModel.java
│   ├── ConversationsModel.java
│   ├── MessagesModel.java
│   ├── MessageRecipientModel.java
│   ├── ForumThreadsModel.java
│   ├── ForumReplyModel.java
│   ├── AnnouncementsModel.java
│   ├── TimetableModel.java
│   ├── TasksModel.java
│   ├── NotesModel.java
│   └── LogsModel.java
├── Entities/
│   ├── UserManagement/
│   │   ├── Users.java
│   │   ├── Roles.java
│   │   └── Enrollments.java
│   ├── RoomManagement/
│   │   ├── Courses.java
│   │   ├── Topics.java
│   │   ├── Subtopics.java
│   │   ├── CourseMaterials.java
│   │   ├── Uploads.java
│   │   ├── Assignments.java
│   │   ├── AssignmentSubmission.java
│   │   ├── Grades.java
│   │   ├── CourseGroups.java
│   │   ├── GroupMembers.java
│   │   └── Timetable.java
│   ├── Chats/
│   │   ├── Conversation.java
│   │   ├── Messages.java
│   │   ├── MessageRecipient.java
│   │   ├── ForumThreads.java
│   │   └── ForumReply.java
│   ├── TaskManagement/
│   │   ├── Tasks.java
│   │   └── Notes.java
│   ├── Announcements.java
│   ├── Calendar.java                   # Entirely commented out
│   └── Logs.java
└── Exceptions/
    ├── ApplicationExceptionHandler.java # @RestControllerAdvice
    ├── ResourceNotFoundException.java
    ├── FileNotFoundException.java
    ├── FileStorageException.java
    └── IOException.java
```

### Recommended package layout (for future refactoring)

Per Java conventions, packages should be lowercase and entities singular:

```
co.ke.unicite
├── config/
├── controller/
├── service/
├── repository/
├── dto/
│   ├── request/
│   └── response/
├── entity/
│   ├── user/
│   ├── course/
│   ├── chat/
│   └── task/
├── exception/
└── security/
```

---

## 4. REST API Design

### Conventions

| Aspect | Current State | Recommended |
|--------|--------------|-------------|
| URL style | Mixed (`/user/signup`, `/courses/create`) | Resource-oriented (`/api/v1/users`, `/api/v1/courses`) |
| HTTP methods | POST for create and delete | POST for create, PUT for update, DELETE for delete |
| Response format | Raw entity or String | Consistent JSON envelope with status, message, data |
| Versioning | None | URL prefix `/api/v1/` |
| Authentication | None | Bearer token (JWT) in Authorization header |
| Error format | Mixed (String, Map, @ResponseStatus) | Standard `{status, message, timestamp, errors}` |

### Controller-to-Service flow

```
HTTP Request
    │
    ▼
Controller (validates input, delegates to service)
    │
    ▼
Service Interface → Service Implementation (business logic, transaction management)
    │
    ▼
Repository (Spring Data JPA, database access)
    │
    ▼
Entity (JPA, mapped to PostgreSQL table)
    │
    ▼
HTTP Response (entity or DTO serialized to JSON)
```

---

## 5. Security Design (Planned)

### Authentication flow

```
Client                    API Server                  Database
  │                           │                          │
  │  POST /api/v1/auth/login  │                          │
  │  {email, password}        │                          │
  │ ─────────────────────────>│                          │
  │                           │  Find user by email      │
  │                           │ ────────────────────────>│
  │                           │  User record             │
  │                           │ <────────────────────────│
  │                           │                          │
  │                           │  Verify BCrypt hash      │
  │                           │                          │
  │  200 {token, user}        │                          │
  │ <─────────────────────────│                          │
  │                           │                          │
  │  GET /api/v1/courses      │                          │
  │  Authorization: Bearer <token>                       │
  │ ─────────────────────────>│                          │
  │                           │  Validate JWT            │
  │                           │  Extract roles           │
  │                           │  Check RBAC              │
  │  200 {courses}            │                          │
  │ <─────────────────────────│                          │
```

### Role-Based Access Control (RBAC)

| Resource | Student | Instructor | Admin |
|----------|---------|-----------|-------|
| View courses | Read own enrollments | Read/Write own courses | Read/Write all |
| Course materials | Read (enrolled) | Read/Write (own courses) | Read/Write all |
| Assignments | Read/Submit (enrolled) | Create/Grade (own courses) | Read/Write all |
| Users | Read own profile | Read own + enrolled students | Full CRUD |
| Forums | Read/Write (enrolled) | Read/Write (own courses) | Read/Write all |
| Analytics | View own grades | View course analytics | View all |

---

## 6. File Storage Design

### Upload flow

```
Client                          Controller              Service                   File System
  │                                 │                      │                          │
  │  POST /courses/materials/upload │                      │                          │
  │  multipart/form-data            │                      │                          │
  │  (file + metadata)              │                      │                          │
  │ ───────────────────────────────>│                      │                          │
  │                                 │  uploadCourseMaterial│                          │
  │                                 │ ────────────────────>│                          │
  │                                 │                      │  Generate UUID filename  │
  │                                 │                      │  Copy to upload-dir      │
  │                                 │                      │ ────────────────────────>│
  │                                 │                      │  Save Upload entity      │
  │                                 │                      │  Save CourseMaterial     │
  │                                 │                      │                          │
  │  200 {courseMaterial}           │                      │                          │
  │ <───────────────────────────────│                      │                          │
```

### Storage configuration

| Property | Current Value | Recommended |
|----------|--------------|-------------|
| `file.upload-dir` | `/path/to/upload/directory` (placeholder) | `/var/unicite/uploads` or cloud storage |
| Max file size | Spring Boot default (1MB) | Configure `spring.servlet.multipart.max-file-size=50MB` |
| Allowed types | No restriction | Whitelist: PDF, DOCX, PPTX, MP4, JPG, PNG |

---

## 7. Data Flow Overview

```
                    ┌─────────┐
                    │ Student │
                    └────┬────┘
                         │ Enrolls, submits, views
                         ▼
┌──────────┐      ┌─────────────┐      ┌────────────┐
│Instructor│─────>│   Courses   │<─────│ Materials  │
│          │      │             │      │ (files)    │
│ Creates  │      └──────┬──────┘      └────────────┘
│ courses, │             │
│ grades,  │     ┌───────┼────────┐
│ announces│     │       │        │
└──────────┘     ▼       ▼        ▼
           ┌─────────┐┌──────┐┌────────┐
           │Assignmts││Groups││Timetabl│
           └────┬────┘└──┬───┘└────────┘
                │        │
                ▼        ▼
           ┌─────────┐┌────────────┐
           │Submissns││GroupMembers│
           └────┬────┘└────────────┘
                │
                ▼
           ┌─────────┐      ┌──────────┐
           │ Grades  │      │ Analytics│
           └─────────┘      └──────────┘
```

---

## 8. Spring Data REST Auto-Exposed Endpoints

Spring Data REST automatically creates HATEOAS endpoints for all `JpaRepository` interfaces. These appear at paths derived from the entity class name (pluralized). Current auto-exposed paths include:

| Entity | Auto-Exposed Path | Notes |
|--------|------------------|-------|
| Roles | `/roleses` | Used for role creation before user signup |
| Courses | `/courseses` | Overlaps with custom `/courses/**` controller |
| Enrollments | `/enrollmentses` | Only access path (no custom controller) |
| Assignments | `/assignmentses` | Only access path |
| AssignmentSubmission | `/assignmentSubmissions` | Only access path |
| Grades | `/gradeses` | Only access path |
| Conversation | `/conversations` | Only access path |
| Messages | `/messageses` | Only access path |
| ForumThreads | `/forumThreadses` | Only access path |
| Announcements | `/announcementses` | Only access path |
| Timetable | `/timetables` | Only access path |
| Tasks | `/taskses` | Only access path |
| Notes | `/noteses` | Only access path |

These auto-generated endpoints provide basic CRUD but lack business logic validation. Custom controllers should eventually replace them for all production use.

---

## 9. External Integration Points (Planned)

| Integration | Purpose | Protocol |
|------------|---------|----------|
| Educational Library APIs | Access books, articles, past papers | REST (HTTP client) |
| USSD Gateway | Offline notifications and basic interactions | USSD protocol via gateway provider |
| Payment Gateway | Course enrollment payments (if applicable) | REST + webhook callbacks |
| Email Service | Password reset, notifications | SMTP or email API (e.g., SendGrid) |
