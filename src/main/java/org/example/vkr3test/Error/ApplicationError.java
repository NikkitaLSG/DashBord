package org.example.vkr3test.Error;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "application_errors")
public class ApplicationError {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String errorMessage;

    private String errorType;
    private String requestPath;
    private String requestMethod;
    private Integer statusCode;

    @Column(columnDefinition = "TEXT")
    private String stackTrace;
}