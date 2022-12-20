FROM gradle:7.6-jdk11 AS build

WORKDIR /app

ENV CONFIG_SERVER_IP localhost
ENV EUREKA_SERVER_IP localhost

COPY ./build/libs/Consumer-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "Consumer-0.0.1-SNAPSHOT.jar"]
