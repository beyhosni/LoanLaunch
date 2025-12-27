FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY loan-launch-common ./loan-launch-common
COPY loan-launch-events ./loan-launch-events
COPY risk-scoring-service ./risk-scoring-service
RUN mvn clean package -pl risk-scoring-service -am -DskipTests
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/risk-scoring-service/target/*.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]
