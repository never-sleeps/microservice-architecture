FROM maven:latest as builder
LABEL maintainer="iakonyakina@gmail.com"
RUN mkdir /app
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:8
RUN mkdir /app
WORKDIR /app
COPY --from=builder /app/target/user-service-0.0.1-SNAPSHOT.jar .
EXPOSE 8080 
ENTRYPOINT ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]