package net.fiv.testgithubapi.service;

import net.fiv.testgithubapi.dto.BranchDto;
import net.fiv.testgithubapi.exception.NoSuchUserException;
import net.fiv.testgithubapi.model.Branch;
import net.fiv.testgithubapi.model.Repository;
import net.fiv.testgithubapi.dto.RepositoryDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    private final RestTemplate restTemplate;

    public GitHubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RepositoryDto> getUserRepositories(final String username) {
        final String repoUrl = "https://api.github.com/users/" + username + "/repos";
        try {
            ResponseEntity<Repository[]> response = restTemplate.getForEntity(repoUrl, Repository[].class);

            Repository[] allRepos = response.getBody();

            return Arrays.stream(allRepos)
                    .filter(repo -> !repo.isFork())
                    .map(repo -> {
                        var branches = getBranches(repo.getOwner().getLogin(), repo.getName());
                        return new RepositoryDto(repo.getName(), repo.getOwner().getLogin(), branches);
                    }).collect(Collectors.toList());

        } catch (HttpClientErrorException.NotFound e) {
            throw new NoSuchUserException("User not found");
        }
    }

    private List<BranchDto> getBranches(final String owner, final String repoName) {
        final String branchesUrl = "https://api.github.com/repos/" + owner + "/" + repoName + "/branches";
        ResponseEntity<Branch[]> response = restTemplate.getForEntity(branchesUrl, Branch[].class);
        Branch[] branches = response.getBody();
        return Arrays.stream(branches)
                .map(branch -> new BranchDto(branch.getName(), branch.getCommit().getSha()))
                .collect(Collectors.toList());
    }
}
