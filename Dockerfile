FROM openjdk:8
ARG MAIL_API=target/*.jar
COPY ${MAIL_API} api.jar
ENTRYPOINT ["java", "-jar", "./api.jar"]