FROM eclipse-temurin:17

WORKDIR /app

COPY build/libs/java.security.day.2.exercise-0.0.1-SNAPSHOT.jar /app/runnalbe-0.0.1-SNAPSHOT.jar

EXPOSE 4000

ENTRYPOINT ["java", "-jar", "runnable-0.0.1-SNAPSHOT.jar"]
