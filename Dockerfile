#etapa 1.- Compilacion
FROM gradle:9.5.1-jdk21 AS build
#copiar el contenido de la aplicacion
COPY --chown=gradle:gradle . /app
WORKDIR /app
##corre el proceso de bootjar
RUN gradle bootJar --no-daemon

#etapa 1.- ejecucion
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar platzi_play.jar
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=prod","-jar","platzi_play.jar"]
