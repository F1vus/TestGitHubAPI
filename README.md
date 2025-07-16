# GitHub Repositories API

## Description
This application consumes the GitHub API to list all **non-fork** repositories of a given user, including each branch and its last commit SHA.

## Specification
### `/api/repos/{username}` endpoint
To retrieve information about all public GitHub repositories for a given user.

## Technologies
- Java 21
- Spring Boot 3
- RestTemplate

## How to Run
```bash
./mvnw spring-boot:run
```
The application runs on `localhost:8080`
