FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY build/libs/eureka-service-0.0.1.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]