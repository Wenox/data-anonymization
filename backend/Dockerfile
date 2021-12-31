FROM openjdk:17.0.1-slim
COPY . /usr/src/anonymisation_backend
RUN apt-get update --no-install-recommends \
    && apt-get install -y postgresql-client --no-install-recommends \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/* \
    && cd ~ \
    && echo *:*:*:*:postgres > .pgpass \
    && chmod 0600 .pgpass
WORKDIR /usr/src/anonymisation_backend
CMD ["./mvnw", "spring-boot:run"]
