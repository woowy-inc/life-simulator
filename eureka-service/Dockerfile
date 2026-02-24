FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY eureka-service/build/libs/eureka-service-*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]