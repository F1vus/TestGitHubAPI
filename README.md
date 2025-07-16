# GitHub Repositories API

## Description
This application consumes the GitHub API to list all **non-fork** repositories of a given user, including each branch and its last commit SHA.

## Specification
### `/api/repos/{username}` endpoint
To retrieve information about all public GitHub repositories for a given user.

## API Endpoints

### **Normal request**

### Example Request:
```http
GET http://localhost:8080/api/repos/F1vus
```
### Example Response:
```json
[
    {
        "repositoryName": "TNTEvent",
        "ownerLogin": "F1vus",
        "branches": [
            {
                "name": "master",
                "lastCommitSha": "c05ab4ad9787d769dfa3eb23f368a97dd8fb24a3"
            }
        ]
    }
]
```

### **If user not found**

### Example Request:
```http
GET http://localhost:8080/api/repos/asijsdfgndfubdunifudiuidb21
```
### Example Response:
```json
{
    "message": "User not found",
    "status": 404
}
```

## Technologies
- Java 21
- Spring Boot 3
- RestTemplate

## How to Run
```bash
./mvnw spring-boot:run
```
The application runs on `localhost:8080`
