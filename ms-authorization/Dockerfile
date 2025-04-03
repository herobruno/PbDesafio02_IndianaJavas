FROM maven:3-openjdk-17 AS build

COPY src /ms-authorization/src
COPY pom.xml /ms-authorization

WORKDIR /ms-authorization
RUN mvn clean install -DskipTests

FROM openjdk:17-alpine

COPY --from=build /ms-authorization/target/ms-authorization-0.0.1-SNAPSHOT.jar /ms-authorization/ms-authorization.jar

WORKDIR /ms-authorization

EXPOSE 8083

CMD ["java", "-jar", "ms-authorization.jar"]

