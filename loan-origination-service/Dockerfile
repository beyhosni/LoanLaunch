# Multi-stage build for Loan Origination Service
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -pl loan-origination-service -am -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY --from=build /app/loan-origination-service/target/*.jar app.jar
EXPOSE 8087
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8087/api/actuator/health || exit 1
ENTRYPOINT ["java", "-jar", "app.jar"]
