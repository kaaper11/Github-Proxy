package com.kaaper.githubProxy.exception;

import com.kaaper.githubProxy.dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalHandlerException {
    @ExceptionHandler(RepoNotFoundException.class)
    public ExceptionDto repoNotFound(RepoNotFoundException e) {
        log.error("GithubProxy exception: status={}, message={}", HttpStatus.NOT_FOUND, e.getMessage());
        return new ExceptionDto(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ExceptionDto handleException(RepoNotFoundException e) {
        log.error("Unexpected exception occurred", e);
        return new ExceptionDto(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
