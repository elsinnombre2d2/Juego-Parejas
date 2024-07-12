# Utiliza una imagen de Maven con JDK 17 para construir la aplicación
FROM maven:3.8.4-openjdk-17-slim AS build

# Define el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el archivo pom.xml y las carpetas de configuración de Maven
COPY pom.xml ./
COPY src ./src/

# Empaqueta la aplicación usando Maven
RUN mvn -B -DskipTests clean package

################################################################################

# Utiliza una imagen ligera de OpenJDK para ejecutar la aplicación
FROM openjdk:17-jdk-slim AS run

# Define el directorio de trabajo en el contenedor
WORKDIR /app

# Copia el JAR construido desde la etapa de construcción anterior
COPY --from=build /app/target/*.jar /app/app.jar

# Expone el puerto 8080 en el contenedor
EXPOSE 8080

# Define el comando para ejecutar la aplicación Spring Boot
CMD ["java", "-jar", "/app/app.jar"]
