package org.example.vkr3test.Metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
public class MetricController {
    private final MetricService service;
    private final Counter requestCounter = Counter.builder("api.metrics.requests")
            .register(Metrics.globalRegistry);

    @GetMapping
    public ResponseEntity<List<Metric>> getMetrics(
            @RequestParam @NotBlank String type,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        requestCounter.increment();
        return ResponseEntity.ok(service.getMetrics(type, from, to));
    }

    @PostMapping
    public ResponseEntity<Metric> saveMetric(@Valid @RequestBody Metric metric) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.saveMetric(metric));
    }
}