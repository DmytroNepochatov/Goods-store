FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
ENV ZIPKIN_URL=http://zipkin:9411
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/app.jar"]