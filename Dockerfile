FROM eclipse-temurin:21

WORKDIR /app

COPY target/menu-0.0.1-SNAPSHOT.jar /app/menu-crud.jar

ENTRYPOINT [ "java", "-jar", "menu-crud.jar"]

EXPOSE 8080