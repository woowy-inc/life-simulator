FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY build/libs/notification-service-*.jar app.jar

EXPOSE 9592

ENTRYPOINT ["java", "-jar", "app.jar"]