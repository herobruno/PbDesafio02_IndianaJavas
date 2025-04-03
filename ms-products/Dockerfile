# Etapa 1: Build do projeto com Maven
FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /ms-products
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean install -DskipTests

# Etapa 2: Imagem final para rodar a aplicação
FROM openjdk:17-jdk-slim
WORKDIR /ms-products
COPY --from=build /ms-products/target/ms-products-0.0.1-SNAPSHOT.jar ms-products.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "ms-products.jar"]
