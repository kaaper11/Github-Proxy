package com.kaaper.githubProxy.controller;

import com.kaaper.githubProxy.service.GithubService;
import com.kaaper.githubProxy.dto.ReposDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/repositories")
public class GithubController {
    private final GithubService githubService;

    @GetMapping("/{owner}/{repositoryName}")
    public ReposDataDto getRepo(@PathVariable String owner, @PathVariable String repositoryName) {
        return githubService.getRepo(owner, repositoryName);
    }

    @PostMapping("/{owner}/{repositoryName}")
    public ReposDataDto createRepo(@PathVariable String owner, @PathVariable String repositoryName) {
        return githubService.createRepo(owner, repositoryName);
    }

}
