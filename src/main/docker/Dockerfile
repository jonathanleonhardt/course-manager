FROM maven:3.8.8-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM maven:3.8.8-eclipse-temurin-21-alpine

WORKDIR /app

COPY --from=build /app/target/challenge-0.0.1-SNAPSHOT.jar .

CMD ["java", "-jar", "challenge-0.0.1-SNAPSHOT.jar"]
