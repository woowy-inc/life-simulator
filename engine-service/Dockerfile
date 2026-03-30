FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY engine-service/build/libs/engine-service-*.jar app.jar
EXPOSE 9797
ENTRYPOINT ["java", "-jar", "app.jar"]