# Estágio 1: Build da aplicação com Maven/Gradle
FROM gradle:8.5-jdk21-alpine AS build
WORKDIR /home/gradle/src
COPY *.gradle ./
COPY src ./src
RUN gradle build --no-daemon -x test

# Estágio 2: Criação da imagem final otimizada
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]