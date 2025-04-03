FROM maven:3-openjdk-17 AS build

COPY src /gateway/src
COPY pom.xml /gateway

WORKDIR /gateway
RUN mvn clean install -DskipTests

FROM openjdk:17-alpine

COPY --from=build /gateway/target/gateway-0.0.1-SNAPSHOT.jar /gateway/gateway.jar

WORKDIR /gateway

EXPOSE 8080

CMD ["java", "-jar", "gateway.jar"]