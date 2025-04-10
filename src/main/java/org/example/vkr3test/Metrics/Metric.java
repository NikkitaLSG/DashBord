package org.example.vkr3test.Metrics;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Metric {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private Double value;
    private LocalDateTime timestamp;

    // Геттеры
    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Double getValue() {
        return value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Сеттеры
    public void setId(Long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}