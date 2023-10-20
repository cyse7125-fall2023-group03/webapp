# Stage 1:  Start with a base image containing Java runtime
FROM openjdk:17-slim
RUN apt-get update && apt-get install -y maven
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy project source pkg 
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Expose port 8080
EXPOSE 8082

# Set the startup command to execute the jar
CMD ["java", "-jar", "target/webapp-0.0.1-SNAPSHOT.jar"]