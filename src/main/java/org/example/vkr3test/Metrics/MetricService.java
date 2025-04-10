package org.example.vkr3test.Metrics;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class MetricService {
    private final MetricRepository repository;

    // ✅ вручную добавленный конструктор
    public MetricService(MetricRepository repository) {
        this.repository = repository;
    }

    public List<Metric> getMetrics(String type, LocalDateTime from, LocalDateTime to) {
        return repository.findByTypeAndTimestampBetween(type, from, to);
    }

    public Metric saveMetric(Metric metric) {
        return repository.save(metric);
    }
}
