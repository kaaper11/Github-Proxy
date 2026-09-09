package com.kaaper.githubProxy.controller;

import com.kaaper.githubProxy.service.GithubService;
import com.kaaper.githubProxy.dto.ReposDataDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/repositories")
@Slf4j
public class GithubController {
    private final GithubService githubService;

    @GetMapping("/local/{owner}/{repositoryName}")
    @ResponseStatus(HttpStatus.FOUND)
    public ReposDataDto getRepo(@PathVariable String owner, @PathVariable String repositoryName) {
        log.info("GET /local/{}/{} request received", owner, repositoryName);
        return githubService.getRepo(owner, repositoryName);
    }

    @PostMapping("/{owner}/{repositoryName}")
    @ResponseStatus(HttpStatus.CREATED)
    public ReposDataDto createRepo(@PathVariable String owner, @PathVariable String repositoryName) {
        log.info("POST /local/{}/{} request received", owner, repositoryName);
        return githubService.createRepo(owner, repositoryName);
    }

    @PutMapping("/{owner}/{repositoryName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRepo(@PathVariable String owner, @PathVariable String repositoryName) {
        log.info("PUT /local/{}/{} request received", owner, repositoryName);
        githubService.updateRepo(owner, repositoryName);
    }

    @DeleteMapping("/{owner}/{repositoryName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRepo(@PathVariable String owner, @PathVariable String repositoryName) {
        log.info("DELETE /local/{}/{} request received", owner, repositoryName);
        githubService.deleteRepo(owner, repositoryName);
    }
}
