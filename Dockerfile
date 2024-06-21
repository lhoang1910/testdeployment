# Dockerfile
FROM adoptopenjdk:17-jdk-hotspot as build
WORKDIR /workspace/app

COPY . .

RUN ./gradlew clean build -x test

FROM adoptopenjdk:17-jdk-hotspot
WORKDIR /app
COPY --from=build /workspace/app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
