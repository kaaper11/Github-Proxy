package com.kaaper.githubProxy.exception;

public class RepoNotFoundException extends RuntimeException {
  public RepoNotFoundException(String owner, String repositoryName) {
    super("Repository " + repositoryName + " not found on owner " + owner);
  }
}
