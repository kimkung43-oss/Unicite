# Development Roadmap

## Unicite Integrated Learning System

**Version:** 1.0
**Date:** November 2026

---

## Overview

This roadmap organizes the remaining development work into five phases, ordered by dependency and priority. Each phase lists the modules affected, the work required, and the prerequisites from prior phases.

The current codebase implements approximately 15% of the thesis requirements with functional endpoints, and has another 35% as database schema (entities and repositories) without business logic. This roadmap covers the path from current state to full feature completion.

---

## Phase 1: Foundation and Code Quality

**Goal:** Fix critical issues, establish security, and set up proper engineering practices.

**Prerequisites:** None (starting point).

### 1.1 Security (P0)

| Task | Files Affected | Details |
|------|---------------|---------|
| Add Spring Security dependency | `pom.xml` | Uncomment spring-boot-starter-security and oauth2-client |
| Implement BCrypt password hashing | `UserServiceImpl.java` | Use `BCryptPasswordEncoder` in createUser and updateUser |
| Add JWT authentication | New: `security/` package | `JwtTokenProvider`, `JwtAuthFilter`, `SecurityConfig` |
| Remove passwords from API responses | `Users.java` | Add `@JsonIgnore` on password field, or create response DTOs |
| Implement login endpoint | New: `AuthController.java` | POST `/api/v1/auth/login` returning JWT |
| Add RBAC to all endpoints | `SecurityConfig.java` | Configure role-based URL patterns |

### 1.2 Code Quality (P1)

| Task | Files Affected | Details |
|------|---------------|---------|
| Replace `@Data` with `@Getter`/`@Setter` on entities | All 24 entity classes | Prevents Lombok-generated `equals`/`hashCode`/`toString` issues |
| Switch to constructor injection | All 3 service impls, all 3 controllers | Replace `@Autowired` fields with `@RequiredArgsConstructor` + `final` |
| Add `@Transactional` to service methods | All service implementations | Write methods: `@Transactional`; read methods: `@Transactional(readOnly = true)` |
| Fix `Users.courses` mapping | `Users.java` | Add `mappedBy = "user"`, change to `FetchType.LAZY` |
| Fix `swaggerApiConfig` | `swaggerApiConfig.java` | Add `@Configuration`, rename to `SwaggerApiConfig` |
| Fix URL space | `CourseMaterialsController.java` | Change `/courses/course materials` to `/courses/materials` |
| Remove commented-out code | All files | Clean pass across entire codebase |
| Standardize error responses | `ApplicationExceptionHandler.java` | Create `ErrorResponse` record, handle all exception types consistently |

### 1.3 Database Improvements

| Task | Files Affected | Details |
|------|---------------|---------|
| Switch `ddl-auto` to `validate` | `application.properties` | Prevents data loss on restart |
| Add Flyway and baseline migration | `pom.xml`, `db/migration/V1__baseline.sql` | Capture current schema as V1 |
| Fix misnamed FKs | Migration V2 | `announcements.fk_user` → `fk_course`; `grades.fk_course` → `fk_course_group` |
| Add indexes on FK columns | Migration V3 | Performance improvement for join queries |

### 1.4 Testing Foundation

| Task | Files Affected | Details |
|------|---------------|---------|
| Add H2 test dependency | `pom.xml` | In-memory database for tests |
| Create `application-test.properties` | `src/test/resources/` | H2 config for test profile |
| Write unit tests for UserServiceImpl | New test class | Test createUser, updateUser, deleteUser, exists methods |
| Write unit tests for CourseServiceImpl | New test class | Test CRUD operations |
| Write controller integration tests | New test classes | `@WebMvcTest` with MockMvc |

---

## Phase 2: Core Feature Completion

**Goal:** Add controllers and services for all entities that currently have schema only.

**Prerequisites:** Phase 1 (security and code quality must be in place).

### 2.1 Enrollment Management

| Task | New Files | Endpoints |
|------|----------|-----------|
| `EnrollmentController.java` | Controller | POST/GET/DELETE `/api/v1/enrollments/**` |
| `EnrollmentService.java` | Interface | enroll, withdraw, getByUser, getByCourse |
| `EnrollmentServiceImpl.java` | Implementation | Business logic with validation |
| Unit + integration tests | Test classes | Cover all enrollment scenarios |

### 2.2 Assignment and Grading

| Task | New Files | Endpoints |
|------|----------|-----------|
| `AssignmentController.java` | Controller | CRUD `/api/v1/courses/{id}/assignments/**` |
| `AssignmentService.java` + Impl | Service layer | Create, update, delete, list by course |
| `AssignmentSubmissionController.java` | Controller | POST `/api/v1/assignments/{id}/submit` |
| `GradeController.java` | Controller | POST/GET `/api/v1/grades/**` |
| `GradeService.java` + Impl | Service layer | Grade submission, get grades by student/course |
| Tests | Test classes | Cover assignment lifecycle |

### 2.3 Forum and Messaging

| Task | New Files | Endpoints |
|------|----------|-----------|
| `ForumController.java` | Controller | CRUD `/api/v1/forums/threads/**` |
| `ForumService.java` + Impl | Service layer | Create thread, reply, list, search |
| `ConversationController.java` | Controller | CRUD `/api/v1/conversations/**` |
| `MessageService.java` + Impl | Service layer | Send, receive, list conversations |
| Tests | Test classes | Cover messaging and forum flows |

### 2.4 Announcements

| Task | New Files | Endpoints |
|------|----------|-----------|
| `AnnouncementController.java` | Controller | CRUD `/api/v1/courses/{id}/announcements/**` |
| `AnnouncementService.java` + Impl | Service layer | Create, list by course, list for enrolled user |
| Tests | Test classes | |

### 2.5 Course Materials (Complete)

| Task | Files Affected | Details |
|------|---------------|---------|
| Implement `downloadCourseMaterial` | `CourseMaterialsServiceImpl.java` | Currently returns null |
| Implement `deleteCourseMaterial` | `CourseMaterialsServiceImpl.java` | Currently empty |
| Configure upload directory | `application.properties` | Set to real path |
| Add file type validation | Service layer | Whitelist allowed MIME types |
| Tests | Test classes | Upload, download, delete lifecycle |

---

## Phase 3: Advanced Features

**Goal:** Add timetable, calendar, task/note management, course groups, and analytics.

**Prerequisites:** Phase 2 (core features needed for analytics to have data).

### 3.1 Timetable and Calendar

| Task | New Files | Endpoints |
|------|----------|-----------|
| `TimetableController.java` | Controller | CRUD `/api/v1/courses/{id}/timetable/**` |
| `TimetableService.java` + Impl | Service layer | Create, list by course, list by user enrollments |
| Implement `Calendar.java` entity | Entity | Currently commented out; aggregate view of events |
| `CalendarController.java` | Controller | GET `/api/v1/calendar/me` |
| Tests | Test classes | |

### 3.2 Task and Note Management

| Task | New Files | Endpoints |
|------|----------|-----------|
| `TaskController.java` | Controller | CRUD `/api/v1/tasks/**` |
| `TaskService.java` + Impl | Service layer | Create, complete, list by user |
| `NoteController.java` | Controller | CRUD `/api/v1/notes/**` |
| `NoteService.java` + Impl | Service layer | Create, update, list by user |
| Tests | Test classes | |

### 3.3 Course Groups

| Task | New Files | Endpoints |
|------|----------|-----------|
| `CourseGroupController.java` | Controller | CRUD `/api/v1/courses/{id}/groups/**` |
| `CourseGroupService.java` + Impl | Service layer | Create group, add/remove members |
| Tests | Test classes | |

### 3.4 Analytics and Reporting

| Task | New Files | Endpoints |
|------|----------|-----------|
| `AnalyticsController.java` | Controller | GET `/api/v1/analytics/**` |
| `AnalyticsService.java` + Impl | Service layer | Aggregate grades, submissions, enrollment counts |
| `LogService.java` + Impl | Service layer | Record and query user actions |
| Add logging interceptor | `config/LoggingInterceptor.java` | Auto-log API requests to Logs table |
| Tests | Test classes | |

### 3.5 Notification System

| Task | New Files | Details |
|------|----------|---------|
| `Notification.java` entity | Entity + Repository | Store notifications per user |
| `NotificationController.java` | Controller | GET/PUT `/api/v1/notifications/**` |
| `NotificationService.java` + Impl | Service layer | Create, mark read, list unread |
| Event-driven triggers | Service layer | Generate notifications on assignment creation, deadline approach, announcements |
| Tests | Test classes | |

---

## Phase 4: Frontend and External Integrations

**Goal:** Build the PWA frontend and integrate external services.

**Prerequisites:** Phase 2 and 3 (API must be complete for frontend to consume).

### 4.1 PWA Frontend

| Task | Details |
|------|---------|
| Set up Ionic project | Separate frontend project or mono-repo module |
| Implement authentication pages | Login, registration, password reset |
| Implement dashboard | Role-specific dashboards for student/instructor/admin |
| Implement course views | Course list, course detail, materials, assignments |
| Implement communication pages | Messaging, forums, announcements |
| Implement task/note management | Personal task list and notes |
| Add service workers | Offline caching for materials |
| Add manifest.json | PWA install prompt |
| Responsive design | Test on mobile, tablet, desktop viewports |

### 4.2 External Library API Integration

| Task | Details |
|------|---------|
| Research and select library APIs | Open Library, Google Books, or educational APIs |
| Implement REST client | `LibraryApiClient.java` with WebClient/RestTemplate |
| Create search endpoint | GET `/api/v1/library/search?q={query}` |
| Display in frontend | Integrated search results alongside local materials |

### 4.3 USSD Integration

| Task | Details |
|------|---------|
| Select USSD gateway provider | Africa's Talking, Hubtel, or similar |
| Implement USSD callback handler | Controller for USSD gateway webhooks |
| Support basic flows | View grades, view notifications, view timetable |

### 4.4 Email Integration

| Task | Details |
|------|---------|
| Add Spring Mail dependency | `pom.xml` |
| Configure SMTP | `application.properties` |
| Implement email service | Password reset, notification digest emails |

---

## Phase 5: Gamification and Polish

**Goal:** Add engagement features and optimize for production.

**Prerequisites:** Phase 3 (analytics data needed for badges/leaderboards).

### 5.1 Badges and Leaderboards

| Task | New Files | Details |
|------|----------|---------|
| `Badge.java` entity | Entity + Repository | Define badge types and criteria |
| `UserBadge.java` entity | Entity + Repository | Track earned badges |
| `BadgeService.java` + Impl | Service layer | Evaluate criteria, award badges |
| `LeaderboardController.java` | Controller | GET `/api/v1/courses/{id}/leaderboard` |
| Frontend integration | Ionic pages | Badge display, leaderboard view |

### 5.2 Performance Optimization

| Task | Details |
|------|---------|
| Add database query optimization | N+1 query detection, query plans |
| Implement caching | Spring Cache with Caffeine for read-heavy endpoints |
| Connection pool tuning | HikariCP configuration |
| Load testing | JMeter/Gatling tests for concurrency targets |

### 5.3 Production Hardening

| Task | Details |
|------|---------|
| Enable Spring Boot Actuator | Uncomment dependency, configure endpoints |
| Add structured logging | Logback with JSON format |
| HTTPS configuration | SSL certificate, redirect HTTP to HTTPS |
| Rate limiting | Bucket4j or similar for API rate limiting |
| CORS configuration | Configure allowed origins for frontend |

---

## Summary

| Phase | Focus | Modules Affected | New Endpoints |
|-------|-------|-----------------|---------------|
| 1 | Foundation | Security, all existing code | 6 (auth) |
| 2 | Core features | Enrollment, Assignments, Grades, Forums, Messaging, Announcements, Materials | ~25 |
| 3 | Advanced features | Timetable, Tasks, Notes, Groups, Analytics, Notifications | ~20 |
| 4 | Frontend + integrations | PWA, Library APIs, USSD, Email | ~5 backend |
| 5 | Gamification + polish | Badges, Leaderboard, Performance, Production | ~5 |
