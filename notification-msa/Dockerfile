FROM eclipse-temurin:21-jre-jammy
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} notification-api.jar

ENTRYPOINT ["java","-jar","/notification-api.jar"]
EXPOSE 8080/tcp
