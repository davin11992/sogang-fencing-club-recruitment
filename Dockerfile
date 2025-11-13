#### 1단계: Build Stage ####
FROM gradle:8.7-jdk21 AS builder
WORKDIR /app

# Gradle 캐싱 최적화 → 먼저 gradle 관련 파일만 복사
COPY build.gradle settings.gradle ./
COPY gradle gradle

# 의존성 캐시 다운로드 (optional but recommended)
RUN gradle build -x test --no-daemon || true

# 나머지 프로젝트 복사
COPY . .

# jar 생성
RUN gradle bootJar --no-daemon


#### 2단계: Run Stage ####
FROM eclipse-temurin:21-jre
WORKDIR /app

# builder 단계에서 생성된 jar 복사
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
