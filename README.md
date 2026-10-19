# JobTrack API

A Java REST API for managing a job-search pipeline. The project focuses on backend fundamentals that are easy to review in an interview: resource-oriented endpoints, validation, pagination, database migrations, and a clear separation between web, application, domain, and persistence code.

> Portfolio project in progress. It is a single-user local API prototype; authentication and multi-user isolation are not implemented yet. API and database ports bind to localhost by default.

## Features

- Create, view, update, and delete job applications.
- Track stages: `SAVED`, `APPLIED`, `INTERVIEW`, `OFFER`, `REJECTED`, and `WITHDRAWN`.
- Filter by status and page through results.
- Validate request fields and return standard HTTP errors.
- PostgreSQL persistence with Flyway schema migration and JPA schema validation.
- Health endpoint through Spring Boot Actuator.
- A stable pagination response shape and field-level validation errors.

## Stack

Java 21 · Spring Boot 3.5 · Spring Web · Spring Data JPA · PostgreSQL · Flyway · Docker Compose

## Run locally

Requirements: JDK 21, Maven 3.9+, and Docker Compose.

```bash
docker compose up -d postgres
./mvnw spring-boot:run
```

If Maven Wrapper is not present yet, use `mvn spring-boot:run` or generate the wrapper with `mvn wrapper:wrapper`.

The API listens on `http://localhost:8080`. Health is available at `/actuator/health`.

Run the service checks with `mvn test`. GitHub Actions also runs them on pushes and pull requests.

## API examples

Create an application:

```bash
curl -X POST http://localhost:8080/api/v1/applications \
  -H "Content-Type: application/json" \
  -d '{"company":"Example Co.","roleTitle":"AI Application Engineer","jobUrl":"https://example.com/jobs/1","status":"APPLIED","notes":"Follow up next week"}'
```

List newest entries, optionally filtered by stage:

```text
GET /api/v1/applications?page=0&size=20&sort=createdAt,desc
GET /api/v1/applications?status=INTERVIEW&page=0&size=20
```

Other endpoints:

| Method | Path | Purpose |
| --- | --- | --- |
| `GET` | `/api/v1/applications/{id}` | Get one application |
| `PUT` | `/api/v1/applications/{id}` | Replace editable fields |
| `DELETE` | `/api/v1/applications/{id}` | Delete an application |

## Design notes

- The database schema is the source of truth; Hibernate validates it rather than generating tables at startup.
- Status is an enum stored as text to keep records readable and migrations explicit.
- The API uses DTOs so persistence entities are not exposed as the public contract.
- Pagination is delegated to Spring Data so the API can scale beyond an in-memory list.

## Next steps

1. Add authentication and per-user data isolation.
2. Add OpenAPI documentation and request examples.
3. Add integration tests with a disposable PostgreSQL instance.
4. Add reminders and interview events as separate domain resources.
5. Add a small frontend only after the API contract stabilizes.
