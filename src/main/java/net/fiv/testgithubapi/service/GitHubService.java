package net.fiv.testgithubapi.service;

import net.fiv.testgithubapi.dto.BranchDto;
import net.fiv.testgithubapi.model.Branch;
import net.fiv.testgithubapi.model.Repository;
import net.fiv.testgithubapi.dto.RepositoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Service
public class GitHubService {

    private final RestTemplate restTemplate;

    public GitHubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RepositoryDto> getUserRepositories(String username) {
        String repoUrl = "https://api.github.com/users/" + username + "/repos";
        try {
            ResponseEntity<Repository[]> response = restTemplate.getForEntity(repoUrl, Repository[].class);


            Repository[] allRepos = response.getBody();

            return Arrays.stream(allRepos)
                    .filter(repo -> !repo.isFork())
                    .map(repo -> {
                        List<BranchDto> branches = getBranches(repo.getOwner().getLogin(), repo.getName());
                        return new RepositoryDto(repo.getName(), repo.getOwner().getLogin(), branches);
                    }).toList();

        } catch (HttpClientErrorException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    private List<BranchDto> getBranches(String owner, String repoName) {
        String branchesUrl = "https://api.github.com/repos/" + owner + "/" + repoName + "/branches";
        ResponseEntity<Branch[]> response = restTemplate.getForEntity(branchesUrl, Branch[].class);
        Branch[] branches = response.getBody();
        return Arrays.stream(branches)
                .map(branch -> new BranchDto(branch.getName(), branch.getCommit().getSha()))
                .toList();
    }
}
