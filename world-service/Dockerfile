FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY auth-service/build/libs/world-service-*.jar app.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "app.jar"]