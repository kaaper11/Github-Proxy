package com.kaaper.githubProxy.service;

import com.kaaper.githubProxy.client.GithubClient;
import com.kaaper.githubProxy.dto.ReposDataDto;
import com.kaaper.githubProxy.exception.RepoNotFound;
import com.kaaper.githubProxy.mapper.ReposDataMapper;
import com.kaaper.githubProxy.model.ReposData;
import com.kaaper.githubProxy.repository.GithubRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubService {
    private final GithubClient githubClient;
    private final ReposDataMapper reposDataMapper;
    private final GithubRepository githubRepository;

    public ReposDataDto getRepo(String owner, String repo) {
        ReposData reposData = githubRepository.findByOwnerAndRepositoryName(owner, repo)
                .orElseThrow(() -> new RepoNotFound(owner, repo));
        return reposDataMapper.toDto(reposData);
    }

    public ReposDataDto createRepo(String owner, String repo) {
        try {
            ReposData reposData = reposDataMapper.fromDto(githubClient.getPostById(owner, repo), owner, repo);
            return reposDataMapper.toDto(githubRepository.save(reposData));
        } catch (FeignException.NotFound e) {
            throw new RepoNotFound(owner, repo);
        }
    }
}
