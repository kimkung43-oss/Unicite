# Unicite ILS

Unicite is an Integrated Learning System (ILS) backend for enhanced blended learning. It provides a REST API for managing users, courses, assignments, grades, messaging, forums, and more.

## Tech Stack

| Component | Version |
|-----------|---------|
| Java | 20 (Temurin) |
| Spring Boot | 3.1.1 |
| PostgreSQL | 12+ |
| Hibernate | 6.2.5 |
| Build | Maven 3.9.2 (via wrapper) |
| API Docs | SpringDoc OpenAPI 2.1.0 |

## Quick Start

### Prerequisites

- **JDK 20** (Temurin recommended). JDK 21 will not work due to a Lombok compatibility issue.
- **PostgreSQL** running on `localhost:5432`.

### Database Setup

```bash
sudo pg_ctlcluster 16 main start
sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'admin';"
sudo -u postgres psql -c "CREATE DATABASE unicite;"
```

### Build and Run

```bash
chmod +x mvnw

# Compile
./mvnw compile

# Run tests
./mvnw test

# Start the application (port 8082)
./mvnw spring-boot:run
```

### Verify

Once running, open the Swagger UI at [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html).

Create a test user:

```bash
# Create a role (roles are also auto-seeded on startup)
curl -X POST http://localhost:8082/roleses \
  -H "Content-Type: application/json" \
  -d '{"roleName":"Instructor","code":"instructor"}'

# Create a user
curl -X POST http://localhost:8082/user/signup \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"pass123","username":"testuser","firstName":"Jane","lastName":"Doe","roleCode":"instructor"}'
```

## Configuration

All settings are in `src/main/resources/application.properties`:

| Property | Default | Notes |
|----------|---------|-------|
| `server.port` | `8082` | HTTP port |
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/unicite` | Database connection |
| `spring.datasource.username` | `postgres` | |
| `spring.datasource.password` | `admin` | |
| `spring.jpa.hibernate.ddl-auto` | `create` | **Drops and recreates** schema on every restart |
| `file.upload-dir` | `/path/to/upload/directory` | Must be changed to a real path for file uploads |

## API Endpoints

### Custom Controllers

| Method | Path | Description |
|--------|------|-------------|
| POST | `/user/signup` | Register a new user |
| PUT | `/user/update` | Update user details |
| POST | `/user/delete?userId={id}` | Soft-delete a user |
| GET | `/user/list/all` | List all users |
| GET | `/user/list/roles/?roleId={id}` | List users by role |
| POST | `/courses/create` | Create a course |
| PUT | `/courses/update` | Update a course |
| POST | `/courses/delete?courseId={id}` | Soft-delete a course |

### Spring Data REST (Auto-Exposed)

Spring Data REST auto-exposes CRUD endpoints for all JPA repositories (e.g., `/roleses`, `/enrollmentses`, `/assignmentses`, `/conversations`, etc.). See the full list in the Swagger UI.

## Project Structure

```
src/main/java/co/ke/unicite/
├── Controllers/          # REST controllers
├── Services/             # Business logic (interfaces + implementations)
├── Repositories/         # Spring Data JPA repositories
├── Models/               # DTOs for request/response
├── Entities/             # JPA entities
│   ├── UserManagement/   # Users, Roles, Enrollments
│   ├── RoomManagement/   # Courses, Assignments, Grades, Groups, etc.
│   ├── Chats/            # Conversations, Messages, Forums
│   └── TaskManagement/   # Tasks, Notes
├── Exceptions/           # Custom exceptions + global handler
└── Config/               # Swagger/OpenAPI configuration
```

## Documentation

Detailed design and development documents are available in the [`docs/`](docs/) directory:

| Document | Description |
|----------|-------------|
| [SRS.md](docs/SRS.md) | Software Requirements Specification (64 requirements, gap analysis) |
| [SYSTEM_DESIGN.md](docs/SYSTEM_DESIGN.md) | Architecture, tech stack, security design |
| [API_SPECIFICATION.md](docs/API_SPECIFICATION.md) | Full endpoint catalog (existing + planned) |
| [DATABASE_DESIGN.md](docs/DATABASE_DESIGN.md) | Schema definitions, relationships, migration strategy |
| [DEVELOPMENT_ROADMAP.md](docs/DEVELOPMENT_ROADMAP.md) | 5-phase implementation plan |
| [TEST_PLAN.md](docs/TEST_PLAN.md) | Testing strategy (unit, integration, security, performance) |
| [DEPLOYMENT_GUIDE.md](docs/DEPLOYMENT_GUIDE.md) | Setup, Docker, CI/CD, monitoring |

## Known Limitations

- **No authentication**: Spring Security is commented out. All endpoints are open.
- **Passwords stored in plain text**: No hashing is applied.
- **Schema reset on restart**: `ddl-auto=create` drops all data on each restart.
- **File uploads broken**: The upload directory path is a placeholder.
- Most features exist as database schema only (entities + repositories) without custom controllers or services. See the [Development Roadmap](docs/DEVELOPMENT_ROADMAP.md) for the implementation plan.

## License

This project is not currently licensed. All rights reserved.
