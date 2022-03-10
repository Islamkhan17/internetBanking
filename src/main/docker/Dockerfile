FROM openjdk:17
ARG JAR_FILE=*.war
COPY ${JAR_FILE} application.war
ENTRYPOINT ["java", "-jar", "application.war"]
EXPOSE 8080