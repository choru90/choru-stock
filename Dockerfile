# 1) 빌드용 스테이지: Gradle로 fat-jar 생성
FROM gradle:8.5-jdk17 AS builder
WORKDIR /home/gradle/project
COPY . .
RUN gradle clean bootJar -x test

# 2) 런타임 스테이지: 생성된 jar만 복사
FROM eclipse-temurin:17-jre
WORKDIR /app

# 애플리케이션 포트
ENV SERVER_PORT=8081

COPY --from=builder /home/gradle/project/build/libs/*.jar app.jar
EXPOSE ${SERVER_PORT}

ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar --server.port=${SERVER_PORT}"]