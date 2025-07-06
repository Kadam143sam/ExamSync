#######################################################################
# -------- 1️⃣  BUILD STAGE ------------------------------------------ #
# Uses a full Maven image (with JDK 17) to compile the source code.   #
#######################################################################
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Create a directory for the project inside the container
WORKDIR /workspace

# Copy only the Maven descriptor first to leverage Docker layer caching
COPY pom.xml .

# Pre‑download dependencies — subsequent builds are faster
RUN mvn -q dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Build the JAR (skip tests on CI; remove -DskipTests if you want them)
RUN mvn clean package -DskipTests

#######################################################################
# -------- 2️⃣  RUNTIME STAGE ---------------------------------------- #
# Smaller JDK image to actually run the compiled JAR.                 #
#######################################################################
FROM eclipse-temurin:17-jdk-jammy

# Non‑root best practice (optional)
RUN useradd --create-home examsync
WORKDIR /home/examsync

# Copy the fat JAR from the build stage
COPY --from=build /workspace/target/*.jar app.jar

# Expose the port Spring Boot listens on (default 8080)
EXPOSE 8080

# Run!
ENTRYPOINT ["java","-jar","app.jar"]
