FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY build/libs/gateway-service-0.0.1.jar app.jar
EXPOSE 9696
ENTRYPOINT ["java", "-jar", "app.jar"]