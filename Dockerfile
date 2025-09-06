# Dockerfile

# Usamos OpenJDK 17
FROM eclipse-temurin:17-jdk

# Directorio de la app dentro del contenedor
WORKDIR /app

# Copiamos el jar construido
COPY target/empresa-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto de Spring Boot
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java","-jar","app.jar"]