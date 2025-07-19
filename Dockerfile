# ────────────────────────────────
# 🏗️  Etapa 1: Build con Maven
# ────────────────────────────────
FROM eclipse-temurin:17-jdk-alpine AS builder

# Instalar Maven
RUN apk add --no-cache maven

# Crear directorio de trabajo
WORKDIR /app

# Copiar el pom primero para aprovechar la caché de Docker
COPY pom.xml .

# Descargar dependencias (se cachea si pom.xml no cambia)
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar el proyecto (sin tests)
RUN mvn clean package -DskipTests

# ─────────────────────────────────────
# 🚀 Etapa 2: Imagen de producción
# ─────────────────────────────────────
FROM eclipse-temurin:17-jre-alpine

# Instalar certificados, fuentes (para Jasper), y utilidades
RUN apk add --no-cache \
    fontconfig \
    ttf-dejavu \
    ca-certificates \
    curl \
    && rm -rf /var/cache/apk/*

# Crear usuario no-root
RUN addgroup -g 1001 -S spring && \
    adduser -S spring -u 1001 -G spring

# Establecer carpeta de trabajo
WORKDIR /app

# Copiar el .jar generado desde el builder
COPY --from=builder --chown=spring:spring /app/target/Samyx-1.0-SNAPSHOT.jar app.jar

# Cambiar a usuario seguro
USER spring

# Variables de entorno para Digital Ocean
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"
ENV TZ=America/Costa_Rica

# Exponer puerto
EXPOSE 8080

# Health check simplificado
HEALTHCHECK --interval=30s --timeout=10s --start-period=120s --retries=5 \
  CMD curl -f http://localhost:8080/login || exit 1

# Comando de arranque
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]