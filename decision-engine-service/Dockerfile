FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY loan-launch-common ./loan-launch-common
COPY loan-launch-events ./loan-launch-events
COPY decision-engine-service ./decision-engine-service
RUN mvn clean package -pl decision-engine-service -am -DskipTests
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/decision-engine-service/target/*.jar app.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "app.jar"]
