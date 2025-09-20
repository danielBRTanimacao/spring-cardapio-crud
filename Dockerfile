FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21

WORKDIR /menu

COPY --from=builder /app/target/menu-0.0.1-SNAPSHOT.jar /menu.jar

ENTRYPOINT [ "java", "-jar", "/menu.jar"]

EXPOSE 8080