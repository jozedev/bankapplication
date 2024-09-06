FROM openjdk:21-jdk
ARG JAR=target/*.jar
COPY ${JAR} account.jar
ENTRYPOINT ["java","-jar","/account.jar"]