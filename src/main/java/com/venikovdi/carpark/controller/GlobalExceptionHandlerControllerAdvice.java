package com.venikovdi.carpark.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException exception, WebRequest webRequest) {
        return super.handleExceptionInternal(
                exception,
                ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getLocalizedMessage()),
                HttpHeaders.EMPTY,
                HttpStatus.FORBIDDEN,
                webRequest);
    }
}
