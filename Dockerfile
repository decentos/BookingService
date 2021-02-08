### build stage
# https://hub.docker.com/_/maven
FROM maven:3-openjdk-11 as build
COPY . /project
WORKDIR /project
RUN mvn install -DskipTests

### final image compillation
# https://hub.docker.com/_/openjdk
FROM openjdk:11-jre
COPY --from=build /project/target/BookingService-1.0-SNAPSHOT.jar /app/BookingService-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/BookingService-1.0-SNAPSHOT.jar"]
CMD []
