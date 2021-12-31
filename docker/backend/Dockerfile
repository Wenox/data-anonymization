FROM openjdk:17.0.1-slim
COPY ../../backend /usr/src/anonymisation_backend
RUN apt-get update && apt install -y postgresql-client
WORKDIR /usr/src/anonymisation_backend
CMD ["./mvnw", "spring-boot:run"]
