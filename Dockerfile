# Build
FROM maven:3.8-openjdk-17 AS build
WORKDIR /price-comparator-app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run
FROM openjdk:17
COPY --from=build /price-comparator-app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]