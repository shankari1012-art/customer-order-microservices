# Use lightweight Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy built jar from target folder
COPY target/order-service-1.0-SNAPSHOT.jar app.jar

# Expose your Spring Boot port
EXPOSE 8082

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]