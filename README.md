# Anonymisation platform

![Test](https://github.com/wenox/data-anonymization/actions/workflows/test.yml/badge.svg)
![Code Quality](https://github.com/wenox/data-anonymization/actions/workflows/quality.yml/badge.svg)
[![CodeFactor](https://www.codefactor.io/repository/github/wenox/data-anonymization/badge?s=d2c8e01307501061b8e2190f75b70ecd6b686ecd)](https://www.codefactor.io/repository/github/wenox/data-anonymization)
[![Sonar - Frontend](https://sonarcloud.io/api/project_badges/measure?project=anonymisation_client&metric=alert_status)](https://sonarcloud.io/dashboard?id=anonymisation_client)
[![Sonar - Backend](https://sonarcloud.io/api/project_badges/measure?project=anonymisation_server&metric=alert_status)](https://sonarcloud.io/dashboard?id=anonymisation_server)

## Prerequisites
- Docker installed (production environment only)

## Environments
- Production
- Development
- Local

Environments are configured from `docker/<env>/.env` and `backend/src/main/resources/application-<env>.properties`.

## Installation

### Production environment

- Start up everything at once:
```shell
cd docker/prod
docker compose up
```

This should take at least few minutes.

### Development environment

- Docker compose for development environment will start only the PostgreSQL container by default:
```shell
cd docker/dev
docker compose up
```

- Start server - this requires JDK 17 to be installed, verify with `java --version`:
```shell
cd backend
./mvnw spring-boot:run
```

- *Alternatively* - the server can be run by specifying `server` profile:
```shell
cd docker/dev
docker compose --profile server up
```

- Start client - this requires node and yarn to be installed, verify with `node -v` and `yarn -v`:
```shell
cd frontend
yarn install
yarn start
```

## Usage

Open [localhost](http://localhost:3000).
