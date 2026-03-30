FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY need-service/build/libs/need-service-*.jar app.jar
EXPOSE 9099
ENTRYPOINT ["java", "-jar", "app.jar"]