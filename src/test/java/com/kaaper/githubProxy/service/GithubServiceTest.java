package com.kaaper.githubProxy.service;

import com.kaaper.githubProxy.client.GithubClient;
import com.kaaper.githubProxy.dto.ReposDataDto;
import com.kaaper.githubProxy.exception.RepoNotFound;
import com.kaaper.githubProxy.mapper.ReposDataMapper;
import com.kaaper.githubProxy.model.ReposData;
import com.kaaper.githubProxy.repository.GithubRepository;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class GithubServiceTest {
    GithubClient githubClient;
    ReposDataMapper reposDataMapper;
    GithubRepository githubRepository;
    GithubService githubService;

    @BeforeEach
    public void setup() {
        this.githubClient = Mockito.mock(GithubClient.class);
        this.githubRepository = Mockito.mock(GithubRepository.class);
        this.reposDataMapper = Mappers.getMapper(ReposDataMapper.class);
        this.githubService = new GithubService(githubClient, reposDataMapper, githubRepository);
    }

    @Test
    public void getRepo_dataCorrect_returnsRepo() {
        // given
        LocalDateTime now = LocalDateTime.now();
        ReposData reposData = new ReposData(1L, "name", "desc", "Url", "5", now,
                "owner", "repo");
        when(githubRepository.findByOwnerAndRepositoryName("owner", "repo"))
                .thenReturn(Optional.of(reposData));

        // when
        ReposDataDto reposDataDto = githubService.getRepo("owner", "repo");

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals("name", reposDataDto.fullName()),
                () -> Assertions.assertEquals("desc", reposDataDto.description()),
                () -> Assertions.assertEquals("Url", reposDataDto.cloneUrl()),
                () -> Assertions.assertEquals(5, reposDataDto.stars()),
                () -> Assertions.assertEquals(now, reposDataDto.createdAt())
        );
    }

    @Test
    public void getRepo_dataCorrect_throwsRepositoryNotFoundException() {
        // given
        String owner = "owner";
        String repo = "repo";

        //when
        RepoNotFound exception = Assertions.assertThrows(RepoNotFound.class,
                () -> githubService.getRepo(owner, repo));

        //then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(exception),
                () -> Assertions.assertTrue(exception.getMessage().contains("owner")),
                () -> Assertions.assertEquals("Repository repo not found on owner owner",
                        exception.getMessage())
        );
    }

    @Test
    public void createRepo_dataCorrect_returnsRepo() {
        // given
        String owner = "owner";
        String repo = "repo";
        LocalDateTime now = LocalDateTime.now();
        ReposDataDto reposDataDto = new ReposDataDto("name", "desc", "Url", 5, now);
        ReposData reposData = reposDataMapper.fromDto(reposDataDto, owner, repo);
        when(githubClient.getPostById(anyString(), anyString())).thenReturn(reposDataDto);
        when(githubRepository.save(any(ReposData.class))).thenReturn(reposData);

        // when
        ReposDataDto reposDataDto1 = githubService.createRepo(owner, repo);

        // then
        Assertions.assertAll(
                () -> Assertions.assertEquals("name", reposDataDto1.fullName()),
                () -> Assertions.assertEquals("desc", reposDataDto1.description()),
                () -> Assertions.assertEquals("Url", reposDataDto1.cloneUrl()),
                () -> Assertions.assertEquals(5, reposDataDto1.stars()),
                () -> Assertions.assertEquals(now, reposDataDto1.createdAt())
        );
    }

    @Test
    public void createRepo_dataCorrect_throwsRepositoryNotFoundException() {
        // given
        Mockito.when(githubClient.getPostById(anyString(), anyString()))
                .thenThrow(FeignException.class);

        // when
        RepoNotFound exception = Assertions.assertThrows(RepoNotFound.class,
                () -> githubService.getRepo("owner", "repo"));

        // then
        Assertions.assertAll(
                () -> Assertions.assertNotNull(exception),
                () -> Assertions.assertTrue(exception.getMessage().contains("owner")),
                () -> Assertions.assertEquals("Repository repo not found on owner owner",
                        exception.getMessage())
        );
    }

}
