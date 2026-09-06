package com.kaaper.githubProxy.exception;

import com.kaaper.githubProxy.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(RepoNotFound.class)
    public ExceptionDto repoNotFound(RepoNotFound e) {
        return new ExceptionDto(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
