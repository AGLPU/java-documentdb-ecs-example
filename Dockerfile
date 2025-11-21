# ============================
# Stage 1 – Build the JAR file
# ============================
FROM eclipse-temurin:21.0.7_10-jdk AS builder

WORKDIR /app

# Copy Maven descriptor first for efficient caching
COPY pom.xml .

# Copy Maven wraper descriptor so no manual maven installtion is required
COPY mvnw .

# Copy all maven configuration that are needed by mvnw
COPY .mvn .mvn

# Download dependencies (cached layer)
RUN chmod +x mvnw

# This command downloads all Maven dependencies before copying your source code.
RUN ./mvnw dependency:go-offline -B

# Copy source and package
COPY src src
RUN ./mvnw clean package -DskipTests

# ============================
# Stage 2 – Create Runtime Image
# ============================
FROM eclipse-temurin:21.0.7_10-jre

WORKDIR /app

# Copy fat JAR from builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
