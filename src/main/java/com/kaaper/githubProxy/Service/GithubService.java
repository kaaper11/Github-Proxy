package com.kaaper.githubProxy.Service;

import com.kaaper.githubProxy.client.GithubClient;
import com.kaaper.githubProxy.dto.ReposDataDto;
import com.kaaper.githubProxy.exception.RepoNotFound;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubService {
    private final GithubClient githubClient;

    public ReposDataDto getRepo(String owner, String repo) {
        try {
            return githubClient.getPostById(owner, repo);
        } catch (FeignException.NotFound e) {
            throw new RepoNotFound(owner, repo);
        }
    }
}
