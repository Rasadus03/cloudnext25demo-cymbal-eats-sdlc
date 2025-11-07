FROM openjdk:26-ea-slim-trixie
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} shopping-cart-api.jar

ENTRYPOINT ["java","-jar","/shopping-cart-api.jar"]
EXPOSE 8080/tcp
