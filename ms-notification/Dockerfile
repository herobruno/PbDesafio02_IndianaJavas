FROM maven:3-openjdk-17 AS build

COPY src /ms-notification/src
COPY pom.xml /ms-notification

WORKDIR /ms-notification
RUN mvn clean install -DskipTests

FROM openjdk:17-alpine

COPY --from=build /ms-notification/target/ms-notification-0.0.1-SNAPSHOT.jar /ms-notification/ms-notification.jar

WORKDIR /ms-notification

EXPOSE 8082

CMD ["java", "-jar", "ms-notification.jar"]

