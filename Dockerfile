FROM openjdk:21-jdk

WORKDIR /app

# COPY file JAR, đã sửa pom.xml để auto build thành ca_keyboards.jar
COPY target/app.jar /app/spring_nodo.jar

EXPOSE 8088

ENTRYPOINT ["java", "-jar", "spring_nodo.jar"]
