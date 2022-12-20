FROM gradle:7.6-jdk11

WORKDIR /app
ENV CONFIG_SERVER_IP localhost
ENV EUREKA_SERVER_IP localhost

COPY ./build/libs/start-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "start-0.0.1-SNAPSHOT.jar"]
