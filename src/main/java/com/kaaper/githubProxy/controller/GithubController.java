package com.kaaper.githubProxy.controller;

import com.kaaper.githubProxy.Service.GithubService;
import com.kaaper.githubProxy.dto.ReposDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/repositories")
public class GithubController {
    private final GithubService githubService;

    @GetMapping("/{owner}/{repositoryName}")
    public ReposDataDto getRepo(@PathVariable String owner, @PathVariable String repositoryName) {
        return githubService.getRepo(owner, repositoryName);
    }

}
