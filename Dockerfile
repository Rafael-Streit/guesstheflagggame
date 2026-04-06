
FROM eclipse-temurin:25.0.1-jdk AS builder

WORKDIR /app

COPY pom.xml .
RUN apt-get update && apt-get install -y maven

COPY src ./src
RUN mvn clean package -Pproduction -DskipTests

FROM eclipse-temurin:25.0.1-jre

WORKDIR /app

COPY --from=builder /app/target/demo-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]