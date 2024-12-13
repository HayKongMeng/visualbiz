FROM openjdk:17
COPY ./target/SpringFinalProject-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "SpringFinalProject-0.0.1-SNAPSHOT.jar"]