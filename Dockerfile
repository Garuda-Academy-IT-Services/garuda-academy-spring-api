FROM openjdk:11-jdk-slim

COPY . .
RUN chmod +x gradlew
RUN ./gradlew clean build
CMD ["java", "-jar", "build/libs/api-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
