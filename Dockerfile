# 1. Build
FROM bellsoft/liberica-openjdk-alpine:17 as builder
WORKDIR /root/app
COPY gradlew build.gradle settings.gradle ./
COPY gradle gradle
RUN chmod +x gradlew && ./gradlew --no-daemon

COPY src src
RUN ./gradlew build -x test --no-daemon

# 2. Serve
FROM bellsoft/liberica-openjre-alpine:17 as runner
WORKDIR /root/app
COPY --from=builder /root/app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
