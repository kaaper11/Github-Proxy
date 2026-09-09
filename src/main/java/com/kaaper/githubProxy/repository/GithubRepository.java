package com.kaaper.githubProxy.repository;
import com.kaaper.githubProxy.model.ReposData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GithubRepository extends JpaRepository<ReposData, Long>{

    Optional<ReposData> findByOwnerAndRepositoryName(String owner, String repositoryName);
}
