package com.kaaper.githubProxy.client;

import com.kaaper.githubProxy.dto.ReposDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "github", url = "https://api.github.com/repos/")
public interface GithubClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{owner}/{repo}", produces = "application/json")
    ReposDataDto getPostById(@PathVariable("owner") String owner, @PathVariable("repo") String repo);
}
