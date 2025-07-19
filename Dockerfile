# Base image con Java 17 (temurin es estable y recomendado)
FROM eclipse-temurin:17-jdk-alpine

# Autor (opcional)
LABEL maintainer="andres@samyx.cr"

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el JAR generado al contenedor
COPY target/*.jar app.jar

# Puerto en el que corre tu app
EXPOSE 8080

# Comando para correr la app
ENTRYPOINT ["java", "-jar", "app.jar"]