FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY gateway-service/build/libs/gateway-service-*.jar app.jar
EXPOSE 9696
ENTRYPOINT ["java", "-jar", "app.jar"]