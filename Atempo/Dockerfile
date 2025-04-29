# 1. Base Image 설정 (Java 17 사용) - 빌드용
FROM eclipse-temurin:17-jdk-alpine AS build

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 리소스를 작업 디렉토리로 복사
COPY . /app

# 4. Gradle 실행 권한 추가 후 빌드 수행
RUN chmod +x /app/gradlew && ./gradlew clean build -x test

# 5. Base Image 설정 (Java 17 사용) - 배포용
FROM eclipse-temurin:17-jdk-alpine

# 6. 작업 디렉토리 설정
WORKDIR /app

# 7. 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*-SNAPSHOT.jar atempo.jar

# 8. 컨테이너 실행 시 기본 명령어 설정
CMD ["java", "-jar", "atempo.jar"]
