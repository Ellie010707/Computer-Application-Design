FROM gradle:7.6-jdk11

WORKDIR /app
ENV CONFIG_SERVER_IP localhost
ENV EUREKA_SERVER_IP localhost

COPY ./build/libs/Server-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "Server-0.0.1-SNAPSHOT.jar"]
