FROM openjdk:17-jdk-slim
LABEL maintainer="ord1naryman.dmitry@gmail.com"
VOLUME /main-app
ADD build/libs/airport-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]