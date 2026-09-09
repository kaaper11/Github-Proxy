package com.kaaper.githubProxy.service;

import com.kaaper.githubProxy.client.GithubClient;
import com.kaaper.githubProxy.dto.ReposDataDto;
import com.kaaper.githubProxy.exception.RepoNotFoundException;
import com.kaaper.githubProxy.mapper.ReposDataMapper;
import com.kaaper.githubProxy.model.ReposData;
import com.kaaper.githubProxy.repository.GithubRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GithubService {
    private final GithubClient githubClient;
    private final ReposDataMapper reposDataMapper;
    private final GithubRepository githubRepository;

    public ReposDataDto getRepo(String owner, String repo) {
        log.debug("Fetching repository: owner={} repository name = {}", owner, repo);
        ReposData reposData = getReposData(owner, repo);
        return reposDataMapper.toDto(reposData);
    }

    public ReposDataDto createRepo(String owner, String repo) {
        log.info("Adding repository owner={} repository name = {}", owner, repo);
        try {
            ReposDataDto reposDataDto = githubClient.getPostById(owner, repo);
            ReposData reposData = reposDataMapper.fromDto(reposDataDto, owner, repo);
            return reposDataMapper.toDto(githubRepository.save(reposData));
        } catch (FeignException.NotFound exception) {
            log.error("Repository not found: owner={} repository name = {}", owner, repo);
            throw new RepoNotFoundException(owner, repo);
        }
    }

    public void updateRepo(String owner, String repo) {
        log.info("Updating repository owner={} repository name = {}", owner, repo);
        ReposData reposData = getReposData(owner, repo);
        try {
            ReposDataDto reposDataDto = githubClient.getPostById(owner, repo);
            reposData.setFullName(reposDataDto.fullName());
            reposData.setDescription(reposDataDto.description());
            reposData.setCloneUrl(reposDataDto.cloneUrl());
            reposData.setStars(reposDataDto.stars().toString());
            githubRepository.save(reposData);
        } catch (FeignException.NotFound exception) {
            log.error("Repository not found: owner={} repository name = {}", owner, repo);
            throw new RepoNotFoundException(owner, repo);
        }
    }

    public void deleteRepo(String owner, String repo) {
        log.info("Deleting repository  owner={} repository name = {}", owner, repo);
        ReposData reposData = getReposData(owner, repo);

        githubRepository.delete(reposData);
    }

    private ReposData getReposData(String owner, String repo) {
        return githubRepository.findByOwnerAndRepositoryName(owner, repo)
                .orElseThrow(() -> {
                    log.error("Repository not found: owner={} repository name = {}", owner, repo);
                    return new RepoNotFoundException(owner, repo);
                });
    }
}
