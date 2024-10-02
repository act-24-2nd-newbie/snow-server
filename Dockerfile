FROM bellsoft/liberica-runtime-container:jdk-17-musl as builder
WORKDIR /app
COPY . .
RUN chmod +x gradlew && ./gradlew build -x test

FROM bellsoft/liberica-runtime-container:jre-17-slim-musl as runner
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]