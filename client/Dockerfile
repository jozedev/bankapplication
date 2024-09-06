FROM openjdk:21-jdk
ARG JAR=target/*.jar
COPY ${JAR} client.jar
ENTRYPOINT ["java","-jar","/client.jar"]