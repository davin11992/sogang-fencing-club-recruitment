#### 1단계: Build Stage ####
FROM gradle:8.7-jdk21 AS builder
WORKDIR /app

# 전체 프로젝트 복사 (성능 문제 없고 구조 안정됨)
COPY . .

# Gradle 빌드
RUN chmod +x gradlew
RUN ./gradlew clean bootJar --no-daemon

#### 2단계: Run Stage ####
FROM eclipse-temurin:21-jre
WORKDIR /app

# builder 단계에서 생성된 jar 복사
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
