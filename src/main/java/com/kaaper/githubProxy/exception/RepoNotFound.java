package com.kaaper.githubProxy.exception;

public class RepoNotFound extends RuntimeException {
  public RepoNotFound(String owner, String repositoryName) {
    super("Repository " + repositoryName + " not found on owner " + owner);
  }
}
