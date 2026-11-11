# Test Plan

## Unicite Integrated Learning System

**Version:** 1.0
**Date:** November 2026
**Test Framework:** JUnit 5
**Build Tool:** Maven (Surefire plugin)

---

## 1. Testing Strategy Overview

The testing strategy follows the thesis methodology (Chapter 6) and covers five levels:

```
┌─────────────────────────────────────────┐
│         Manual / UAT Testing            │  Swagger UI, browser, stakeholder
├─────────────────────────────────────────┤
│        Security Testing                 │  OWASP checks, auth flows
├─────────────────────────────────────────┤
│      Performance Testing                │  Load, stress, concurrency
├─────────────────────────────────────────┤
│     Integration / API Testing           │  MockMvc, @SpringBootTest
├─────────────────────────────────────────┤
│         Unit Testing                    │  JUnit 5 + Mockito
└─────────────────────────────────────────┘
```

### Current state

- 1 test file: `UniciteApplicationTests.java` with a single `contextLoads()` test
- No unit tests, integration tests, or performance tests

### Target coverage

| Layer | Target Coverage | Priority |
|-------|----------------|----------|
| Service layer | 80% line coverage | High |
| Controller layer | 70% line coverage | High |
| Repository layer | 60% line coverage | Medium |
| Entity/DTO layer | Covered via integration tests | Low |

---

## 2. Unit Testing

### 2.1 Framework and Tools

| Tool | Purpose |
|------|---------|
| JUnit 5 | Test framework |
| Mockito | Mocking dependencies |
| AssertJ | Fluent assertions |
| JaCoCo | Code coverage reporting |

### 2.2 Test Structure

```
src/test/java/co/ke/unicite/
├── UniciteApplicationTests.java           # Context loads (existing)
├── service/
│   ├── UserServiceImplTest.java
│   ├── CourseServiceImplTest.java
│   ├── CourseMaterialsServiceImplTest.java
│   ├── EnrollmentServiceImplTest.java     # Phase 2
│   ├── AssignmentServiceImplTest.java     # Phase 2
│   ├── GradeServiceImplTest.java          # Phase 2
│   ├── ForumServiceImplTest.java          # Phase 2
│   ├── MessageServiceImplTest.java        # Phase 2
│   └── ...
├── controller/
│   ├── UserControllerTest.java
│   ├── CourseControllerTest.java
│   ├── CourseMaterialsControllerTest.java
│   └── ...
└── repository/
    ├── UsersRepositoryTest.java
    ├── CoursesRepositoryTest.java
    └── ...
```

### 2.3 Service Layer Tests

Each service implementation should have tests covering:

**UserServiceImpl test cases:**

| Test Case | Description | Input | Expected |
|-----------|-------------|-------|----------|
| createUser_success | Valid user model | Complete UsersModel | User saved with hashed password and role |
| createUser_roleNotFound | Invalid role code | UsersModel with bad roleCode | ResourceNotFoundException |
| existsByEmail_true | Email exists | Existing email string | Returns true |
| existsByEmail_false | Email does not exist | New email string | Returns false |
| existsByUsername_true | Username exists | Existing username | Returns true |
| deleteUser_success | Valid userId | Existing user ID | User.isActive set to false |
| deleteUser_notFound | Invalid userId | Non-existent ID | NoSuchElementException |
| updateUser_success | Valid update model | UsersModel with existing userId | Updated fields saved |
| updateUser_notFound | Invalid userId | Non-existent userId | ResourceNotFoundException |
| getUsersByRole_success | Valid roleId | Existing role ID | List of users with that role |
| getUsersByRole_notFound | Invalid roleId | Non-existent role ID | ResourceNotFoundException |

**CourseServiceImpl test cases:**

| Test Case | Description | Input | Expected |
|-----------|-------------|-------|----------|
| createCourse_success | Valid course model | Complete CoursesModel | Course saved with instructor |
| createCourse_userNotFound | Invalid userId | CoursesModel with bad userId | ResourceNotFoundException |
| updateCourse_success | Valid update | CoursesModel with existing courseId | Updated fields saved |
| updateCourse_notFound | Invalid courseId | Non-existent courseId | ResourceNotFoundException |
| deleteCourse_success | Valid courseId | Existing course ID | Course.isActive set to false |
| deleteCourse_notFound | Invalid courseId | Non-existent ID | ResourceNotFoundException |
| existsByCourseName_true | Name exists | Existing course name | Returns true |
| getCoursesByUser_success | Valid userId | Existing user ID | List of courses |

### 2.4 Naming Convention

Test method names follow: `methodUnderTest_scenario_expectedBehavior`

Example: `createUser_emailAlreadyExists_throwsException`

---

## 3. Integration Testing

### 3.1 Repository Tests

Use `@DataJpaTest` with an embedded H2 database:

```
src/test/resources/application-test.properties:
  spring.datasource.url=jdbc:h2:mem:testdb
  spring.datasource.driver-class-name=org.h2.Driver
  spring.jpa.hibernate.ddl-auto=create-drop
  spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
```

**Repository test cases:**

| Repository | Test Cases |
|-----------|------------|
| UsersRepository | findByEmail, existsByEmail, existsByUsername, findByIsActive, findByRoles |
| CoursesRepository | findByUser, findByCourseCode, countByUser, existsByCourseName |
| RoleRepository | findByRoleName, findByCode, existsByCode |
| EnrollmentsRepository | findByUser, findByCourse |

### 3.2 Controller Integration Tests

Use `@WebMvcTest` with `MockMvc` for testing HTTP layer:

**UserController test cases:**

| Test Case | HTTP | Path | Status | Validation |
|-----------|------|------|--------|------------|
| signup_validUser | POST | `/user/signup` | 200 | Returns user JSON |
| signup_duplicateEmail | POST | `/user/signup` | 400 | Returns error message |
| signup_duplicateUsername | POST | `/user/signup` | 400 | Returns error message |
| signup_invalidEmail | POST | `/user/signup` | 400 | Validation error map |
| signup_missingFields | POST | `/user/signup` | 400 | Validation error map |
| listAll_success | GET | `/user/list/all` | 200 | Returns user list |
| deleteUser_success | POST | `/user/delete?userId=1` | 200 | Confirmation message |
| updateUser_success | PUT | `/user/update` | 200 | Confirmation message |

**CourseController test cases:**

| Test Case | HTTP | Path | Status | Validation |
|-----------|------|------|--------|------------|
| create_validCourse | POST | `/courses/create` | 200 | Returns course JSON |
| create_duplicateName | POST | `/courses/create` | 400 | Error message |
| create_missingName | POST | `/courses/create` | 400 | Error message |
| update_success | PUT | `/courses/update` | 200 | Confirmation |
| delete_success | POST | `/courses/delete?courseId=1` | 200 | Confirmation |

### 3.3 Full Application Tests

Use `@SpringBootTest` with a running PostgreSQL (or Testcontainers):

| Test Case | Description |
|-----------|-------------|
| contextLoads | Spring context starts without errors (existing) |
| fullUserLifecycle | Create role → signup → update → delete → verify |
| fullCourseLifecycle | Create user → create course → update → delete → verify |
| enrollmentFlow | Create user + course → enroll → verify → withdraw |

---

## 4. API Functional Testing

### 4.1 Manual Testing via Swagger UI

Swagger UI is available at `http://localhost:8082/swagger-ui/index.html`.

**Checklist for each endpoint:**

- [ ] Valid request returns expected response and status code
- [ ] Invalid input returns 400 with clear error message
- [ ] Missing required fields return validation errors
- [ ] Non-existent resource IDs return 404
- [ ] Duplicate detection works (email, username, course name)

### 4.2 Automated API Tests with curl/httpie

Create a shell script `scripts/smoke-test.sh` that exercises all implemented endpoints:

```bash
# 1. Create role
POST /roleses {"roleName":"TestRole","code":"test"}

# 2. Create user
POST /user/signup {"email":"test@test.com","password":"pass","username":"tester",...}

# 3. Create course
POST /courses/create {"courseName":"Test Course","courseCode":"TC01",...}

# 4. List users
GET /user/list/all

# 5. Verify Swagger UI
GET /swagger-ui/index.html → 200
```

---

## 5. Security Testing

### 5.1 Authentication Tests (after Phase 1 implementation)

| Test Case | Description | Expected |
|-----------|-------------|----------|
| Access protected endpoint without token | GET `/api/v1/courses` without Authorization header | 401 Unauthorized |
| Access with invalid token | GET with malformed JWT | 401 Unauthorized |
| Access with expired token | GET with expired JWT | 401 Unauthorized |
| Access with valid token | GET with valid JWT | 200 OK |
| Student accesses admin endpoint | GET `/api/v1/users` with student role | 403 Forbidden |
| Login with correct credentials | POST `/api/v1/auth/login` | 200 + JWT |
| Login with wrong password | POST `/api/v1/auth/login` | 401 Unauthorized |

### 5.2 OWASP Checklist

| Check | Description | Tool |
|-------|-------------|------|
| SQL Injection | Test query parameters with SQL payloads | Manual + OWASP ZAP |
| XSS | Test input fields with script tags | Manual + OWASP ZAP |
| CSRF | Verify CSRF protection on state-changing endpoints | Manual |
| Sensitive data exposure | Verify passwords not in responses, HTTPS enforced | Manual review |
| Broken access control | Verify RBAC on every endpoint | Automated tests |
| Mass assignment | Verify DTOs don't expose internal fields | Code review |

---

## 6. Performance Testing

### 6.1 Targets (from thesis)

| Metric | Target |
|--------|--------|
| Concurrent users | 100 without performance degradation |
| API response time (CRUD) | < 500ms (p95) |
| API response time (analytics) | < 2000ms (p95) |
| Database query time | < 100ms for indexed queries |

### 6.2 Load Test Scenarios

Use Apache JMeter or Gatling:

| Scenario | Virtual Users | Duration | Endpoints |
|----------|--------------|----------|-----------|
| Steady state | 50 concurrent | 10 min | GET courses, GET assignments, GET user profile |
| Peak load | 100 concurrent | 5 min | Mixed read/write operations |
| Stress test | 200 concurrent | 5 min | Identify breaking point |
| Spike test | 0→150→0 | 3 min | Sudden traffic surge |

### 6.3 Database Performance

| Test | Description |
|------|-------------|
| N+1 query detection | Enable Hibernate SQL logging, check for excessive queries |
| Index effectiveness | Run EXPLAIN ANALYZE on common queries |
| Connection pool | Monitor HikariCP under load (active, idle, pending) |

---

## 7. Test Data Management

### 7.1 Seed Data for Testing

Roles (seeded on startup):
- `instructor` / "Instructor"
- `learner` / "Learner"

Test users:
- Instructor: `instructor@test.com` / role: instructor
- Student 1: `student1@test.com` / role: learner
- Student 2: `student2@test.com` / role: learner
- Admin: `admin@test.com` / role: admin (requires admin role to be added)

Test courses:
- "Introduction to Computer Science" / CS101
- "Data Structures" / CS201

### 7.2 Test Data Reset

Since `ddl-auto=create` drops and recreates on restart, test data must be re-seeded for each test run. After migrating to Flyway:
- Use `@Sql` annotations to load test data per test class
- Use `@Transactional` on tests for automatic rollback

---

## 8. CI/CD Integration

### 8.1 GitHub Actions Workflow

```yaml
name: CI
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    services:
      postgres:
        image: postgres:16
        env:
          POSTGRES_DB: unicite
          POSTGRES_USER: postgres
          POSTGRES_PASSWORD: admin
        ports:
          - 5432:5432
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '20'
          distribution: 'temurin'
      - run: ./mvnw test
      - run: ./mvnw jacoco:report  # after JaCoCo plugin is added
```

### 8.2 Quality Gates

| Gate | Threshold | Tool |
|------|-----------|------|
| All tests pass | 100% | Maven Surefire |
| Code coverage | >= 60% | JaCoCo |
| No critical vulnerabilities | 0 critical | OWASP Dependency-Check |
| Build succeeds | Clean build | Maven |
