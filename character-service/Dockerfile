FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY character-service/build/libs/character-service-*.jar app.jar
EXPOSE 9292
ENTRYPOINT ["java", "-jar", "app.jar"]