# LoanLaunch - AI-Powered Intelligent Lending Platform

LoanLaunch is an intelligent lending platform for small businesses that automates risk assessment using alternative data sources (cash-flow, SaaS metrics, open banking) to provide loan decisions within hours instead of weeks.

## 🏗️ Architecture

This is a microservices-based platform built with:
- **Java 21**
- **Spring Boot 3.2.1**
- **Spring Cloud Gateway**
- **Apache Kafka** (event-driven architecture)
- **PostgreSQL** (one database per service)
- **Docker** & **Docker Compose**

### Microservices

1. **API Gateway** - Single entry point, routing, and authentication
2. **Auth/IAM Service** - User authentication and authorization
3. **Organization Service** - Manage organizations and users ✅
4. **Data Ingestion Service** - Simulate open banking data ingestion
5. **Data Normalization Service** - Normalize and analyze financial data
6. **Risk Scoring Service** - Calculate risk scores using rule-based logic
7. **Decision Engine Service** - Automated loan approval decisions
8. **Loan Origination Service** - Manage loan applications
9. **Notification Service** - Send notifications (email, SMS)
10. **Audit Service** - Event logging and audit trail

## 🚀 Quick Start

### Prerequisites

- **Java 21** (JDK)
- **Maven 3.9+**
- **Docker** & **Docker Compose**

### Running Locally

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd LoanLaunch
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Start infrastructure and services**
   ```bash
   docker-compose up -d
   ```

4. **Verify services are running**
   ```bash
   docker-compose ps
   ```

### Accessing Services

- **Organization Service API**: http://localhost:8082/api
- **Organization Service Swagger UI**: http://localhost:8082/api/swagger-ui.html
- **Kafka UI**: http://localhost:8090
- **PostgreSQL**: localhost:5432 (user: postgres, password: postgres)

## 📋 Testing the Organization Service

### Create an Organization

```bash
curl -X POST http://localhost:8082/api/organizations \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Acme Corp",
    "legalName": "Acme Corporation Inc.",
    "taxId": "12-3456789",
    "industry": "Technology",
    "foundedDate": "2020-01-15",
    "employeeCount": 25,
    "annualRevenue": 1500000.00,
    "addressLine1": "123 Main St",
    "city": "San Francisco",
    "state": "CA",
    "postalCode": "94102",
    "country": "USA",
    "phone": "+1-555-0100",
    "email": "contact@acmecorp.com",
    "website": "https://acmecorp.com"
  }'
```

### Get Organization by ID

```bash
curl http://localhost:8082/api/organizations/{id}
```

### List All Organizations

```bash
curl "http://localhost:8082/api/organizations?page=0&size=10"
```

### Update Organization

```bash
curl -X PUT http://localhost:8082/api/organizations/{id} \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Acme Corporation",
    "employeeCount": 30
  }'
```

### Delete Organization

```bash
curl -X DELETE http://localhost:8082/api/organizations/{id}
```

## 📊 Kafka Events

The Organization Service publishes the following events to Kafka:

- **OrganizationCreatedEvent** - Published when a new organization is created
- **OrganizationUpdatedEvent** - Published when an organization is updated

You can monitor these events in the Kafka UI at http://localhost:8090

## 🗂️ Project Structure

```
loan-launch/
├── pom.xml                          # Parent POM
├── docker-compose.yml               # Infrastructure setup
├── loan-launch-common/              # Shared utilities
├── loan-launch-events/              # Event definitions
└── organization-service/            # Organization microservice
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── com/loanllaunch/organization/
    │   │   │       ├── adapter/
    │   │   │       │   ├── in/rest/        # REST controllers & DTOs
    │   │   │       │   └── out/
    │   │   │       │       ├── kafka/      # Kafka publishers
    │   │   │       │       └── persistence/ # JPA repositories
    │   │   │       ├── application/
    │   │   │       │   └── service/        # Business logic
    │   │   │       └── domain/
    │   │   │           └── model/          # Domain entities
    │   │   └── resources/
    │   │       ├── application.yml
    │   │       └── db/migration/           # Flyway migrations
    │   └── test/
    ├── Dockerfile
    └── pom.xml
```

## 🛠️ Development

### Building a Single Service

```bash
mvn clean package -pl organization-service -am
```

### Running Tests

```bash
mvn test
```

### Viewing Logs

```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f organization-service
```

### Stopping Services

```bash
docker-compose down
```

### Cleaning Up (including volumes)

```bash
docker-compose down -v
```

## 📚 API Documentation

Each service provides OpenAPI documentation via Swagger UI:

- Organization Service: http://localhost:8082/api/swagger-ui.html

## 🔧 Configuration

Services can be configured via environment variables in `docker-compose.yml`:

- `DB_HOST`, `DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD` - Database configuration
- `KAFKA_BOOTSTRAP_SERVERS` - Kafka connection
- `SERVER_PORT` - Service port

## 📈 Monitoring

- **Health Checks**: Each service exposes `/api/actuator/health`
- **Kafka UI**: Monitor topics, messages, and consumers at http://localhost:8090

## 🎯 Next Steps

1. Implement remaining microservices (Auth, Data Ingestion, etc.)
2. Add API Gateway with Spring Cloud Gateway
3. Implement end-to-end loan application flow
4. Add comprehensive testing
5. Add security (JWT, OAuth2)

## 📝 License

[Your License Here]

## 👥 Contributors

[Your Team Here]
