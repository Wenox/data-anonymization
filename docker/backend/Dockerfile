FROM openjdk:17.0.1-slim
COPY ../../backend /usr/src/anonymisation_backend
RUN apt-get update && apt-get install -y postgresql-client --no-install-recommends
WORKDIR /usr/src/anonymisation_backend
CMD ["./mvnw", "spring-boot:run"]
