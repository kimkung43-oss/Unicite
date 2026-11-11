# Deployment and Operations Guide

## Unicite Integrated Learning System

**Version:** 1.0
**Date:** November 2026

---

## 1. Local Development Setup

### 1.1 Prerequisites

| Requirement | Version | Notes |
|-------------|---------|-------|
| JDK | 20 (Temurin) | JDK 21 will **not** work (Lombok incompatibility) |
| PostgreSQL | 12+ (16 recommended) | Installed locally or via Docker |
| Maven | 3.9.2 | Bundled via `./mvnw` wrapper |
| Git | 2.x | Version control |

### 1.2 Install JDK 20

Download Temurin JDK 20 from [Adoptium](https://adoptium.net/temurin/releases/?version=20):

```bash
# Linux (x64)
curl -sL "https://github.com/adoptium/temurin20-binaries/releases/download/jdk-20.0.2%2B9/OpenJDK20U-jdk_x64_linux_hotspot_20.0.2_9.tar.gz" -o jdk20.tar.gz
sudo mkdir -p /usr/lib/jvm/java-20-temurin
sudo tar -xzf jdk20.tar.gz -C /usr/lib/jvm/java-20-temurin --strip-components=1

# Set JAVA_HOME (add to ~/.bashrc or ~/.zshrc)
export JAVA_HOME=/usr/lib/jvm/java-20-temurin
export PATH="$JAVA_HOME/bin:$PATH"
```

Verify:
```bash
java -version
# openjdk version "20.0.2" 2023-07-18
```

### 1.3 Install and Configure PostgreSQL

```bash
# Ubuntu/Debian
sudo apt-get install -y postgresql postgresql-client

# Start the service
sudo pg_ctlcluster 16 main start

# Set password and create database
sudo -u postgres psql -c "ALTER USER postgres PASSWORD 'admin';"
sudo -u postgres psql -c "CREATE DATABASE unicite;"

# Verify connectivity
PGPASSWORD=admin psql -h localhost -U postgres -d unicite -c "SELECT 1;"
```

### 1.4 Clone and Build

```bash
git clone https://github.com/kimkung43-oss/Unicite.git
cd Unicite
chmod +x mvnw

# Compile
./mvnw compile

# Run tests
./mvnw test

# Start the application
./mvnw spring-boot:run
```

The application starts on **port 8082**. Swagger UI is available at `http://localhost:8082/swagger-ui/index.html`.

### 1.5 Verify the Setup

```bash
# Create a role
curl -s -X POST http://localhost:8082/roleses \
  -H "Content-Type: application/json" \
  -d '{"roleName":"Instructor","code":"INSTRUCTOR"}'

# Create a user
curl -s -X POST http://localhost:8082/user/signup \
  -H "Content-Type: application/json" \
  -d '{"email":"test@unicite.co.ke","password":"test123","username":"testuser","firstName":"Test","lastName":"User","roleCode":"INSTRUCTOR"}'

# List users
curl -s http://localhost:8082/user/list/all
```

---

## 2. Configuration

### 2.1 Application Properties

All configuration is in `src/main/resources/application.properties`:

| Property | Default | Description |
|----------|---------|-------------|
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/unicite` | Database URL |
| `spring.datasource.username` | `postgres` | Database user |
| `spring.datasource.password` | `admin` | Database password |
| `server.port` | `8082` | HTTP port |
| `spring.jpa.hibernate.ddl-auto` | `create` | Schema strategy (change for production) |
| `file.upload-dir` | `/path/to/upload/directory` | File upload directory (must exist) |

### 2.2 Spring Profiles (Recommended)

Create separate property files for each environment:

**`application-dev.properties`:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/unicite
spring.datasource.username=postgres
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
file.upload-dir=/tmp/unicite/uploads
```

**`application-prod.properties`:**
```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USER}
spring.datasource.password=${DATABASE_PASSWORD}
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
file.upload-dir=/var/unicite/uploads
server.error.include-message=never
```

**`application-test.properties`:**
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
```

Activate a profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## 3. Production Deployment Checklist

### 3.1 Before Deployment

- [ ] **Change `ddl-auto`** from `create` to `validate` (prevents data loss)
- [ ] **Set up database migrations** (Flyway or Liquibase)
- [ ] **Hash all existing passwords** (run a one-time migration script)
- [ ] **Enable Spring Security** (uncomment dependencies, configure)
- [ ] **Configure file upload directory** to a real, writable path
- [ ] **Set `server.error.include-message=never`** (prevent stack trace leaks)
- [ ] **Remove Swagger UI in production** or secure it behind authentication
- [ ] **Set strong database credentials** via environment variables
- [ ] **Enable HTTPS** with a valid SSL certificate
- [ ] **Configure CORS** for the frontend domain only

### 3.2 Environment Variables

Use environment variables instead of hardcoded values in production:

| Variable | Description |
|----------|-------------|
| `DATABASE_URL` | JDBC connection string |
| `DATABASE_USER` | Database username |
| `DATABASE_PASSWORD` | Database password |
| `JWT_SECRET` | Secret key for JWT signing |
| `FILE_UPLOAD_DIR` | Absolute path for file uploads |
| `SPRING_PROFILES_ACTIVE` | Active profile (`prod`) |

### 3.3 Build for Production

```bash
# Build a fat JAR
./mvnw clean package -DskipTests

# The JAR is at:
# target/unicite-0.0.1-SNAPSHOT.jar

# Run it:
java -jar target/unicite-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

---

## 4. Deployment Options

### 4.1 Linux Server (Direct)

```bash
# Create system user
sudo useradd -r -s /bin/false unicite

# Create directories
sudo mkdir -p /opt/unicite /var/unicite/uploads /var/log/unicite
sudo chown unicite:unicite /opt/unicite /var/unicite/uploads /var/log/unicite

# Copy JAR
sudo cp target/unicite-0.0.1-SNAPSHOT.jar /opt/unicite/unicite.jar

# Create systemd service
sudo cat > /etc/systemd/system/unicite.service << 'EOF'
[Unit]
Description=Unicite ILS
After=postgresql.service
Requires=postgresql.service

[Service]
Type=simple
User=unicite
ExecStart=/usr/lib/jvm/java-20-temurin/bin/java -jar /opt/unicite/unicite.jar --spring.profiles.active=prod
EnvironmentFile=/opt/unicite/unicite.env
Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
EOF

# Create environment file
sudo cat > /opt/unicite/unicite.env << 'EOF'
DATABASE_URL=jdbc:postgresql://localhost:5432/unicite
DATABASE_USER=unicite_app
DATABASE_PASSWORD=<strong-password>
JWT_SECRET=<random-256-bit-key>
FILE_UPLOAD_DIR=/var/unicite/uploads
EOF
sudo chmod 600 /opt/unicite/unicite.env

# Enable and start
sudo systemctl daemon-reload
sudo systemctl enable unicite
sudo systemctl start unicite
sudo systemctl status unicite
```

### 4.2 Docker (Recommended for Cloud)

**Dockerfile:**
```dockerfile
FROM eclipse-temurin:20-jre
WORKDIR /app
COPY target/unicite-0.0.1-SNAPSHOT.jar unicite.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "unicite.jar", "--spring.profiles.active=prod"]
```

**docker-compose.yml:**
```yaml
version: '3.8'
services:
  db:
    image: postgres:16
    environment:
      POSTGRES_DB: unicite
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: admin
    volumes:
      - pgdata:/var/lib/postgresql/data
    ports:
      - "5432:5432"

  app:
    build: .
    depends_on:
      - db
    environment:
      DATABASE_URL: jdbc:postgresql://db:5432/unicite
      DATABASE_USER: postgres
      DATABASE_PASSWORD: admin
      FILE_UPLOAD_DIR: /data/uploads
    ports:
      - "8082:8082"
    volumes:
      - uploads:/data/uploads

volumes:
  pgdata:
  uploads:
```

```bash
docker-compose up -d
```

---

## 5. CI/CD Pipeline Design

### 5.1 GitHub Actions

Create `.github/workflows/ci.yml`:

```yaml
name: CI/CD Pipeline

on:
  push:
    branches: [master]
  pull_request:
    branches: [master]

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
        options: >-
          --health-cmd pg_isready
          --health-interval 10s
          --health-timeout 5s
          --health-retries 5
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '20'
          distribution: 'temurin'
          cache: maven
      - run: chmod +x mvnw
      - run: ./mvnw test

  build:
    needs: test
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/master'
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-java@v4
        with:
          java-version: '20'
          distribution: 'temurin'
          cache: maven
      - run: chmod +x mvnw
      - run: ./mvnw clean package -DskipTests
      - uses: actions/upload-artifact@v4
        with:
          name: unicite-jar
          path: target/unicite-0.0.1-SNAPSHOT.jar
```

### 5.2 Pipeline Stages

```
Push/PR → Compile → Test → Build JAR → (master only) Deploy
```

---

## 6. Monitoring and Logging

### 6.1 Application Logging

Spring Boot logs to stdout by default. Configure `src/main/resources/logback-spring.xml` for file output:

```xml
<configuration>
  <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <file>/var/log/unicite/unicite.log</file>
    <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
      <fileNamePattern>/var/log/unicite/unicite.%d{yyyy-MM-dd}.log</fileNamePattern>
      <maxHistory>30</maxHistory>
    </rollingPolicy>
    <encoder>
      <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
  </appender>

  <root level="INFO">
    <appender-ref ref="FILE" />
  </root>
</configuration>
```

### 6.2 Health Checks (after enabling Actuator)

Uncomment the Actuator dependency in `pom.xml`, then:

| Endpoint | Description |
|----------|-------------|
| `/actuator/health` | Application health status |
| `/actuator/info` | Application info |
| `/actuator/metrics` | JVM and application metrics |

### 6.3 Database Monitoring

```sql
-- Active connections
SELECT count(*) FROM pg_stat_activity WHERE datname = 'unicite';

-- Slow queries
SELECT query, calls, mean_exec_time
FROM pg_stat_statements
ORDER BY mean_exec_time DESC
LIMIT 10;

-- Table sizes
SELECT relname, pg_size_pretty(pg_total_relation_size(relid))
FROM pg_catalog.pg_statio_user_tables
ORDER BY pg_total_relation_size(relid) DESC;
```

---

## 7. Backup and Recovery

### 7.1 Database Backup

```bash
# Full backup
pg_dump -h localhost -U postgres unicite > backup_$(date +%Y%m%d).sql

# Restore
psql -h localhost -U postgres unicite < backup_20261111.sql
```

### 7.2 Automated Backup (cron)

```bash
# Add to crontab (daily at 2 AM)
0 2 * * * pg_dump -h localhost -U postgres unicite | gzip > /var/backups/unicite/backup_$(date +\%Y\%m\%d).sql.gz
```

### 7.3 File Upload Backup

```bash
# Sync uploads to backup location
rsync -avz /var/unicite/uploads/ /var/backups/unicite/uploads/
```
