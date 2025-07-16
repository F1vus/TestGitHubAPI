package net.fiv.testgithubapi.controller;

import net.fiv.testgithubapi.dto.RepositoryDto;
import net.fiv.testgithubapi.service.GitHubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GitHubController {

    private final GitHubService service;

    public GitHubController(GitHubService service) {
        this.service = service;
    }

    @GetMapping("/repos/{username}")
    public ResponseEntity<?> getRepositories(@PathVariable String username) {
        try {
            List<RepositoryDto> repositories = service.getUserRepositories(username);
            return ResponseEntity.ok(repositories);
        } catch (ResponseStatusException ex) {
            Map<String, Object> errorBody = Map.of(
                    "status", ex.getStatusCode().value(),
                    "message", ex.getMessage()
            );
            return ResponseEntity.status(ex.getStatusCode()).body(errorBody);
        }
    }
}

