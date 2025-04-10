package org.example.vkr3test.Error;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorRepository errorRepository;

    public GlobalExceptionHandler(ErrorRepository errorRepository) {
        this.errorRepository = errorRepository;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex, WebRequest request) {
        HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();

        ApplicationError error = new ApplicationError();
        error.setTimestamp(LocalDateTime.now());
        error.setErrorMessage(ex.getMessage());
        error.setErrorType(ex.getClass().getSimpleName());
        error.setStackTrace(Arrays.toString(ex.getStackTrace()));
        error.setRequestPath(httpRequest.getRequestURI());
        error.setRequestMethod(httpRequest.getMethod());

        if (ex instanceof ResponseStatusException) {
            error.setStatusCode(((ResponseStatusException) ex).getStatusCode().value());
        }

        errorRepository.save(error);

        return ResponseEntity.internalServerError()
                .body("Произошла ошибка: " + ex.getMessage());
    }
}