# Étape 1 : builder avec Maven + JDK 11
FROM maven:3.8.7-eclipse-temurin-11 AS build

WORKDIR /app

# Copie des fichiers de configuration Maven et du code
COPY pom.xml .
COPY src ./src

# Build de l’artefact sans exécuter les tests
RUN mvn clean verify package

# Étape 2 : runtime
FROM eclipse-temurin:11-jre

WORKDIR /app

# Copier le jar généré depuis l’étape build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]

