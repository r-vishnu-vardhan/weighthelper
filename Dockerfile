# ---- Build Stage ----
FROM maven:3.8.6-openjdk-8-slim AS build

WORKDIR /app

# Cache Maven dependencies for faster rebuilds
COPY pom.xml .
COPY pittest-config.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build application (including tests if needed)
RUN mvn clean package

# ---- Runtime Stage ----
FROM openjdk:8-jdk-alpine

WORKDIR /app

# Create non-root user for security
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Change ownership to non-root user
RUN chown -R appuser:appgroup /app

# Switch to non-root user
USER appuser

# Optional: expose port (if running web server or API)
# EXPOSE 8080

# Optional: health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD java -version || exit 1

# Environment
ENV JAVA_OPTS="-Xmx256m -Xms128m -XX:+UseG1GC -XX:+UseContainerSupport"

# Default: just confirm JAR creation (for Jenkins pipeline output)
ENTRYPOINT ["sh", "-c", "echo 'BMI Calculator build complete. JAR ready!' && java -jar app.jar || true"]

# For Jenkins, running tests/mutation should happen during build phase above
