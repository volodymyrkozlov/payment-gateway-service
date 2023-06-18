# Payment Gateway

## Introduction

This repository contains a simple payment gateway service that accepts credit card payments, does validation, persists
to a database, streams submitted payment results to a file and provides a REST API to retrieve persisted data. <br>

**Entity relationship diagram** <br>

![ERD](/assets/ERD.png)

The project also shows how to create a multi-module Maven project, manage dependencies and plugins, create REST
controllers, create business logic layers using SOLID principles and structural patterns, connect to a database, manage
database entities, create liquibase scripts, do bean validation and proper error management, create a docker file,
create OpenAPI docs.

## Build

To build payment gateway service application, run the following command. <br>
`mvn clean install`<br>

*It will run unit tests and install*
dependencies.

## Run

**Run application in a local machine**<br>
Navigate to **payment-gateway-service** directory and run the following command: <br>
`mvn spring-boot:run`

*The application will start on port `8080`. The swagger UI will be available by the
link: http://localhost:8080/payment-gateway-service/swagger-ui/index.html* <br>

**Run application in a docker container**<br>
Run the following command: <br>
`docker compose up` <br>

*The application will start on port `8080`. The swagger UI will be available by the
link: http://localhost:8080/payment-gateway-service/swagger-ui/index.html as well.* <br>
<br>
The detailed requirements are found in [ASSIGNMENT.md](ASSIGNMENT.md).
