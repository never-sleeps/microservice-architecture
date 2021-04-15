FROM openjdk:11.0-jdk
ENV JAVA_OPTS=""

COPY build/libs/order-service-0.0.1-SNAPSHOT.jar /deploy/app.jar
ENTRYPOINT java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /deploy/app.jar