#--> Construir el docker file (1)
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copiar pom y wrapper primero para aprovechar la cache
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Damos permisos al wrapper
RUN chmod +x mvnw

# Descargamos dependencias sin compilar todo
RUN ./mvnw dependency:go-offline -B

# Copiar el codigo fuente
COPY src ./src

# Compilamos y generamos el .jar (Sin ejecutar test)
RUN ./mvnw clean package -DskipTests -B

#--> Ejecutamos (2)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar el jar generado desde el builder
COPY --from=builder /app/target/*.jar app.jar

# Exponemos el puerto 8080
EXPOSE 8080

# Crear un usuario no root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]