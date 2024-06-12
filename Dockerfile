FROM gradle:jdk11
COPY . .
RUN gradle clean
RUN gradle build
RUN gradle jar

FROM openjdk:11
RUN ls -la
COPY --from=build /build/libs/api-0.0.1-SNAPSHOT.jar api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api.jar"]


