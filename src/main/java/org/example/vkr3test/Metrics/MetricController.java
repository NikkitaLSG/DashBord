package org.example.vkr3test.Metrics;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/metrics")

public class MetricController {
    private final MetricService service;
    public MetricController(MetricService service) {
        this.service = service;
    }

    @GetMapping
    public List<Metric> getMetrics(
            @RequestParam String type,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return service.getMetrics(type, from, to);
    }

    @PostMapping
    public Metric saveMetric(@RequestBody Metric metric) {
        metric.setTimestamp(LocalDateTime.now());
        return service.saveMetric(metric);
    }
}

