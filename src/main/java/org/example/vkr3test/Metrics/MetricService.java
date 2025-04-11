package org.example.vkr3test.Metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.example.vkr3test.Metrics.MetricRepository;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MetricService {
    private final MetricRepository repository;
    private final DataSource dataSource;
    private final Counter dbQueryCounter = Counter.builder("app.db.queries.total")
            .description("Total database queries count")
            .register(Metrics.globalRegistry);

    public MetricService(MetricRepository repository, DataSource dataSource) {
        this.repository = repository;
        this.dataSource = dataSource;
    }

    public List<Metric> getMetrics(String type, LocalDateTime from, LocalDateTime to) {
        dbQueryCounter.increment();
        return repository.findByTypeAndTimestampBetween(type, from, to);
    }

    public Metric saveMetric(Metric metric) {
        dbQueryCounter.increment();
        return repository.save(metric);
    }
}