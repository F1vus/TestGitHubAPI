package net.fiv.testgithubapi.controller;

import net.fiv.testgithubapi.dto.RepositoryDto;
import net.fiv.testgithubapi.service.GitHubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GitHubController {

    private final GitHubService service;

    public GitHubController(GitHubService service) {
        this.service = service;
    }

    @GetMapping("/repos/{username}")
    public List<RepositoryDto> getRepositories(@PathVariable String username) {
            return service.getUserRepositories(username);
    }
}

