FROM eclipse-temurin:21.0.1_12-jre-jammy
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
