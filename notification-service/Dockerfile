FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY loan-launch-common ./loan-launch-common
COPY loan-launch-events ./loan-launch-events
COPY notification-service ./notification-service
RUN mvn clean package -pl notification-service -am -DskipTests
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/notification-service/target/*.jar app.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "app.jar"]
