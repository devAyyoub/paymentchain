# Microservices Architecture with Spring Boot

This project implements a complete **microservices architecture** using **Spring Boot** and **Spring Cloud**. It includes business and infrastructure domains, applying best practices for scalability, security, and API management.

## Architecture Overview

### Business Domain Microservices
- **Customer Service**: Manages customer data and operations.
- **Product Service**: Handles product-related functionality.
- **Transaction Service**: Manages transactions and operations between customers and products.

### Infrastructure Domain Microservices
- **Eureka Registry and Discovery**: Service discovery for dynamic registration and lookup.
- **API Gateway**: Central entry point with routing, load balancing, and security features.
- **Config Server**: Centralized configuration management for microservices.
- **Spring Boot Admin**: Monitors and manages microservices health and performance.

### Independent Service
- **Billing Service**: Dedicated service for billing and invoicing.

## Key Features
- **Microservices Architecture**: Decoupled, modular, and scalable architecture.
- **Security**: OAuth2 and CORS for API protection.
- **Service Registry**: Dynamic service discovery with **Eureka Server**.
- **API Gateway**: Centralized request routing and security.
- **Config Server**: Centralized configuration using Spring Cloud Config.
- **Spring Boot Admin**: Real-time monitoring and administration.
- **OpenAPI (Swagger)**: API documentation following OpenAPI Specification.
- **JWT**: Secure authentication with JSON Web Tokens.
- **Keycloak Integration**: OAuth2 and SSO for identity and access management.
- **Dockerized Deployment**: Microservices are packaged as Docker containers.
- **Docker Compose Orchestration**: Full-stack environment setup for E2E testing.
- **Spring WebFlux**: Reactive programming model for non-blocking I/O operations.
- **PostgreSQL Integration**: Data persistence using Spring Data and PostgreSQL.

## Tools & Technologies
- **Spring Boot** and **Spring Cloud** (Eureka, Config Server, Spring Admin)
- **Keycloak** for OAuth2 and SSO
- **Spring Security** for API protection
- **Spring Data JPA** and **PostgreSQL**
- **Docker** and **Docker Compose**
- **Swagger/OpenAPI** for API-first development
- **Reactive programming with Spring WebFlux**

## Setup Instructions
1. Clone this repository:
   ```bash
   git clone https://github.com/your-repo-name
   cd project-folder
2. Build and run all microservices using Docker Compose:
   ``bash
   docker-compose up --build
3. Access the following services:
	•	API Gateway: http://localhost:8081
	•	Eureka Server: http://localhost:8761
	•	Spring Boot Admin: http://localhost:9751
	•	Swagger UI (API Docs): http://localhost:<service-port>/swagger-ui.html

<img width="724" alt="Captura de pantalla 2025-01-15 a las 20 13 27" src="https://github.com/user-attachments/assets/513000ac-5877-42de-9d8a-c8fb4279d944" />



