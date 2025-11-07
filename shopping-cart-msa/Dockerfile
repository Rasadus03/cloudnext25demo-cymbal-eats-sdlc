FROM eclipse-temurin:21-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} shopping-cart-api.jar

ENTRYPOINT ["java","-jar","/shopping-cart-api.jar"]
EXPOSE 8080/tcp