# Usamos una imagen base de OpenJDK
FROM openjdk:21-jdk-slim

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiamos el archivo JAR de la aplicación al contenedor
COPY target/eloqualogger-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto en el que la aplicación estará escuchando
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]