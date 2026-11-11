# AGENTS.md

## Cursor Cloud specific instructions

### Project overview
Unicite ILS (Integrated Learning System) — a Spring Boot 3.1.1 REST API backend for a blended learning LMS. No frontend exists in this repo; the API is testable via Swagger UI at `http://localhost:8082/swagger-ui/index.html`.

### Java version
The project requires **JDK 20** (`<java.version>20</java.version>` in `pom.xml`). JDK 21 will **not** work due to a Lombok compatibility issue (`JCTree$JCImport.qualid` field removal). Temurin JDK 20 is installed at `/usr/lib/jvm/java-20-temurin` and `JAVA_HOME` is set in `~/.bashrc`.

### Database
PostgreSQL must be running on `localhost:5432` with database `unicite`, user `postgres`, password `admin` (configured in `src/main/resources/application.properties`). Hibernate `ddl-auto=create` auto-creates all tables on startup, so no manual migrations are needed — but this also **drops and recreates** the schema on every restart.

### Build and run
```bash
# Start PostgreSQL (if not running)
sudo pg_ctlcluster 16 main start

# Build
./mvnw compile

# Run tests
./mvnw test

# Run the application (port 8082)
./mvnw spring-boot:run
```

### Key gotchas
- The `file.upload-dir` property in `application.properties` points to `/path/to/upload/directory` (a placeholder). File upload endpoints will fail unless this is changed to an existing directory.
- Spring Security / OAuth2 dependencies are commented out in `pom.xml` — no authentication is enforced.
- The Spring Data REST auto-exposes JPA repositories (e.g., `/roleses`, `/courseses`). Custom controllers exist at `/user/**` and `/courses/**`.
- To create a user via `/user/signup`, a role must exist first. Create one via `POST /roleses` with `{"roleName":"...","code":"..."}`.
