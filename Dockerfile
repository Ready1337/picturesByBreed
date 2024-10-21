FROM openjdk:17-alpine
RUN apk update
WORKDIR /opt
COPY target/picturesByBreed-0.0.2-SNAPSHOT.jar .
ENTRYPOINT java -jar picturesByBreed-0.0.2-SNAPSHOT.jar
