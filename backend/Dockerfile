FROM openjdk:17.0.1-slim
RUN apt-get update --no-install-recommends \
    && apt-get install -y postgresql-client --no-install-recommends \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* \
    && cd ~ \
    && echo *:*:*:*:postgres > .pgpass \
    && chmod 0600 .pgpass
COPY . /usr/src/anonymisation_backend
WORKDIR /usr/src/anonymisation_backend
RUN chmod +x mvnw
CMD ["./mvnw", "spring-boot:run"]
