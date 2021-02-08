FROM maven:latest as builder
LABEL maintainer="iakonyakina@gmail.com"
RUN mkdir /app
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:8  
RUN mkdir /app
WORKDIR /app
COPY --from=builder /app/target/homework-02-1.0.jar .
EXPOSE 8080 
ENTRYPOINT ["java", "-jar", "homework-02-1.0.jar"]