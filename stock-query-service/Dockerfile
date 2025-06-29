FROM gradle:8.5-jdk21 AS builder
WORKDIR /home/gradle/project
COPY . .
RUN gradle clean bootJar -x test

FROM eclipse-temurin:21-jre
WORKDIR /app
ENV SERVER_PORT=8080
COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["sh","-c","java -jar /app/app.jar --server.port=${SERVER_PORT}"]
