# Overview

This Spring Boot project demonstrates the integration of Kafka and PostgreSQL using Docker. The application includes a Kafka consumer that processes JSON messages from a Kafka topic and writes them to a PostgreSQL database. For testing purposes, the project also includes a Kafka producer that can send messages to the topic either through a REST controller or a message generator activated via a Spring Boot profile.

### Prerequisites

* Docker and Docker Compose
* Java 17 or higher
* Gradle

### Project Structure

* src/main/java: Contains the main application code, including the Kafka consumer and producer.
* src/main/resources: Contains application properties and configuration files.
* docker-compose.yml: Defines the Docker services for Kafka and PostgreSQL.


### Setup and Running the Application

**Step 1:** Start Kafka and PostgreSQL Containers

To set up the required Kafka and PostgreSQL services, navigate to the **docker** directory and run:

```
docker-compose up -d
```
This command will start the Kafka and PostgreSQL containers in detached mode.

**Step 2:** Build and Run the Spring Boot Application

Ensure you have all dependencies installed and run the project through your IDE or Gradle.

**Step 3:** Send Messages to Kafka

You can send messages to the Kafka topic using the REST controller or the message generator. To use the REST controller, send a POST request to `http://localhost:8080/messages` with a `message` request parameter. To use the message generator, activate the `messageGenerator` Spring Boot profile.