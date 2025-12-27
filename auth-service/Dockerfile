# Multi-stage build for Auth Service

# Stage 1: Build
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copy parent POM
COPY pom.xml .

# Copy shared modules
COPY loan-launch-common ./loan-launch-common
COPY loan-launch-events ./loan-launch-events

# Copy auth service
COPY auth-service ./auth-service

# Build the application
RUN mvn clean package -pl auth-service -am -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy JAR from build stage
COPY --from=build /app/auth-service/target/*.jar app.jar

# Expose port
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8081/api/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
