FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17
COPY --from=builder /build/Administracion/target/Administracion-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
