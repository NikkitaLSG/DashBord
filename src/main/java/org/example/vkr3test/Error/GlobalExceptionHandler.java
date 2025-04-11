package org.example.vkr3test.Error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ErrorRepository errorRepository;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex, WebRequest request) {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();

        ApplicationError error = ApplicationError.builder()
                .timestamp(LocalDateTime.now())
                .errorMessage(ex.getMessage())
                .errorType(ex.getClass().getSimpleName())
                .stackTrace(Arrays.toString(ex.getStackTrace()))
                .requestPath(httpRequest.getRequestURI())
                .requestMethod(httpRequest.getMethod())
                .statusCode(ex instanceof ResponseStatusException ?
                        ((ResponseStatusException) ex).getStatusCode().value() : 500)
                .build();

        errorRepository.save(error);
        log.error("Error occurred: {}", error);

        return ResponseEntity.internalServerError()
                .body(new ErrorResponse("Error occurred", ex.getMessage()));
    }

    @Data
    @AllArgsConstructor
    private static class ErrorResponse {
        private String error;
        private String message;
    }
}