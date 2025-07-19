# ────────────────────────────────
# 🏗️  Etapa 1: Build con Maven
# ────────────────────────────────
FROM eclipse-temurin:17-jdk-alpine AS builder

# Instalar Maven
RUN apk add --no-cache maven

# Crear directorio de trabajo
WORKDIR /app

# Copiar el pom y el código fuente
COPY pom.xml .
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

# Crear directorios necesarios
RUN mkdir -p /app /home/XmlClientes /app/logs && \
    chown -R spring:spring /app /home/XmlClientes

# Cambiar a usuario seguro
USER spring

# Establecer carpeta de trabajo
WORKDIR /app

# Copiar el .jar generado desde el builder
COPY --from=builder --chown=spring:spring /app/target/Samyx-1.0-SNAPSHOT.jar app.jar

# Variables de entorno
ENV SPRING_PROFILES_ACTIVE=prod
ENV SERVER_PORT=8080
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+EnableDynamicAgentLoading"
ENV TZ=America/Costa_Rica

# Exponer puerto
EXPOSE 8080

# Health check (requiere spring-boot-actuator en el classpath)
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Comando de arranque
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]