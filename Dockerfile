# ============================
# Stage 1 – Build the JAR file
# ============================
FROM eclipse-temurin:21.0.7_10-jdk AS builder

WORKDIR /app

# Copy Maven descriptor first
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

# Copy project source
COPY src src

# Build Spring Boot JAR
RUN ./mvnw clean package -DskipTests


# ============================
# Stage 2 – Runtime Image
# ============================
FROM eclipse-temurin:21.0.7_10-jre

WORKDIR /app

# Add DocumentDB SSL certificate
ADD doc-dbCerts.pem /app/doc-dbCerts.pem

# Import into Java truststore
RUN /opt/java/openjdk/bin/keytool -importcert \
    -file /app/doc-dbCerts.pem \
    -alias docdb \
    -keystore /opt/java/openjdk/lib/security/cacerts \
    -storepass changeit -noprompt

# Copy JAR from build stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
