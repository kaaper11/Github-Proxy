package com.kaaper.githubProxy.controller;

import com.kaaper.githubProxy.dto.ReposDataDto;
import com.kaaper.githubProxy.service.GithubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GithubControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    GithubService githubService;

    @Test
    void getRepo_dataCorrect_repReturned() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.now();
        ReposDataDto reposDataDto = new ReposDataDto("name", "desc", "Url", 5, now);
        when(githubService.getRepo(anyString(), anyString())).thenReturn(reposDataDto);

        // when then
        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/local/{owner}/{repositoryName}",
                        "owner", "repo"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.full_name").value("name"))
                .andExpect(jsonPath("$.description").value("desc"))
                .andExpect(jsonPath("$.stargazers_count").value(5))
                .andExpect(jsonPath("$.clone_url").value("Url"));
        verify(githubService).getRepo("owner", "repo");
    }

    @Test
    void createRepo_dataCorrect_repReturned() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.now();
        ReposDataDto reposDataDto = new ReposDataDto("name", "desc", "Url", 5, now);
        when(githubService.createRepo(anyString(), anyString())).thenReturn(reposDataDto);

        // when then
        mockMvc.perform(MockMvcRequestBuilders.post("/repositories/{owner}/{repositoryName}",
                        "owner", "repo"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.full_name").value("name"))
                .andExpect(jsonPath("$.description").value("desc"))
                .andExpect(jsonPath("$.stargazers_count").value(5))
                .andExpect(jsonPath("$.clone_url").value("Url"));
        verify(githubService).createRepo("owner", "repo");
    }

    @Test
    void updateRepo_dataCorrect_updatedRepo() throws Exception {
        // given
        String owner = "owner";
        String repo = "repo";

        // when then
        mockMvc.perform(MockMvcRequestBuilders.put("/repositories/{owner}/{repositoryName}",
                        "owner", "repo"))
                .andExpect(status().isNoContent());
        verify(githubService).updateRepo(owner, repo);
    }

    @Test
    void deleteRepo_dataCorrect_deletedRepo() throws Exception {
        // given
        String owner = "owner";
        String repo = "repo";

        // when then
        mockMvc.perform(MockMvcRequestBuilders.delete("/repositories/{owner}/{repositoryName}",
                        "owner", "repo"))
                .andExpect(status().isNoContent());
        verify(githubService).deleteRepo(owner, repo);
    }
}
