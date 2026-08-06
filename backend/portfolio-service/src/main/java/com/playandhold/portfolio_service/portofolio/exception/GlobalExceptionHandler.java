package com.playandhold.portfolio_service.portofolio.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PortfolioNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handlerPortfolioNotFoundException(
            PortfolioNotFoundException exception,
            HttpServletRequest request
    ) {
        log.warn("The portfolio can not be found -> {}", exception.getMessage());
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity
                .status(status)
                .body(errorResponse);

    }

}