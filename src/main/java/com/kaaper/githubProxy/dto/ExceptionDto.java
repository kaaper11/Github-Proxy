package com.kaaper.githubProxy.dto;

import org.springframework.http.HttpStatus;

public record ExceptionDto(
        String message,
        HttpStatus status
) {
}
