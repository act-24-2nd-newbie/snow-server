FROM bellsoft/liberica-runtime-container:jdk-17-musl as base
WORKDIR /app
COPY gradlew .
COPY *.gradle .
COPY gradle ./gradle
RUN chmod +x gradlew && ./gradlew

FROM base as builder
WORKDIR /app
COPY src src
RUN ./gradlew build -x test

FROM bellsoft/liberica-runtime-container:jre-17-slim-musl as runner
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]