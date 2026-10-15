# PayFlow — Digital Wallet & Payment API

PayFlow is a backend REST API for a digital wallet platform built with Java and Spring Boot. It supports wallet operations, financial transactions, authentication, and asynchronous email verification.

The project focuses on backend engineering concepts such as transaction management, concurrency control, idempotency, caching, and event-driven processing.

## Features

- **Authentication & Authorization** — JWT-based authentication, role-based access control, and password hashing with BCrypt.
- **Email Verification** — OTP verification using Redis and asynchronous email delivery through Kafka and Resend.
- **Wallet Management** — Wallet creation after email verification.
- **Financial Transactions** — Deposits, withdrawals, and wallet-to-wallet transfers.
- **Transaction Safety** — Idempotency support and pessimistic locking for concurrent wallet operations.
- **Admin APIs** — Paginated user, wallet, and transaction management endpoints.
- **Database Migrations** — Version-controlled schema changes using Flyway.
- **Containerization** — Docker and Docker Compose support.

## Tech Stack

| Category | Technologies |
|---|---|
| Language | Java |
| Framework | Spring Boot |
| Database | PostgreSQL |
| Persistence | Spring Data JPA, Hibernate |
| Migrations | Flyway |
| Authentication | Spring Security, JWT, BCrypt |
| Caching & OTP | Redis |
| Messaging | Apache Kafka |
| Email | Resend |
| Containerization | Docker, Docker Compose |

## Architecture Overview

PayFlow uses a layered Spring Boot architecture:

- **Controllers** — Handle HTTP requests and responses.
- **Services** — Implement business logic and transaction workflows.
- **Repositories** — Access persistent data through Spring Data JPA.
- **Entities & DTOs** — Represent database models and API contracts.
- **Security Layer** — Authenticates requests and enforces access control.
- **Kafka Consumers** — Process asynchronous email-verification tasks.

PostgreSQL stores application data, Redis supports OTP workflows, and Kafka decouples email processing from the request lifecycle.

## Financial Operations

### Deposit
Adds funds to the authenticated user's wallet.

### Withdrawal
Withdraws funds from the authenticated user's wallet, subject to balance validation.

### Transfer
Transfers funds between wallets while applying transaction management, idempotency, and pessimistic locking.

The source wallet is determined from the authenticated user rather than accepted from the client request.

## Getting Started

### Prerequisites

- Java version compatible with the project's Maven configuration
- Docker and Docker Compose
- Git

### Clone the repository

```bash
git clone https://github.com/abdallahMoohamed/payflow.git
cd payflow
```

### Configure environment variables

Create a local `.env` file based on `.env.example` and configure the required database, JWT, Redis, Kafka, and email settings.

Never commit `.env` or real credentials to the repository.

### Run with Docker Compose

```bash
docker compose up --build -d
```

Check the container status:

```bash
docker compose ps
```

### Build and test

```bash
./mvnw clean verify
```

## Configuration

Configure the following values using the environment variable names expected by the application:

- PostgreSQL connection settings
- Redis connection settings
- Kafka bootstrap servers
- JWT signing secret and token expiration
- Resend API key

Use `.env.example` as the reference for the actual variable names.

## Security Notes

- Passwords are hashed using BCrypt.
- Protected endpoints require JWT authentication.
- Administrative endpoints require the appropriate role.
- Secrets should be supplied through environment variables.
- Financial operations should be performed within appropriate database transaction boundaries.

## Project Goals

This project demonstrates practical backend development with Spring Boot, secure API design, relational data modeling, concurrency control, and asynchronous messaging.

## Author

**Abdallah Mohamed**

- GitHub: [@abdallahMoohamed](https://github.com/abdallahMoohamed)
