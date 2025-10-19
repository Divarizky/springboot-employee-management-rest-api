# Import Java JDK
FROM openjdk:21-jdk-slim

# Direktori
WORKDIR /app

# Copy Gradle wrapper dan source (src)
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .

COPY src/ src/

# Build
RUN chmod +x gradlew
RUN ./gradlew build -x test

# Untuk data Aplikasi sementara
VOLUME /tmp

# Port
EXPOSE 8080

# Jalankan Aplikasi
ENTRYPOINT ["java", "-jar", "build/libs/employee-management-0.0.1-SNAPSHOT.jar"]