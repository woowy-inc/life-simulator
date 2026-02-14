FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

RUN apk add --no-cache curl

COPY build/libs/eureka-service-0.0.1.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]