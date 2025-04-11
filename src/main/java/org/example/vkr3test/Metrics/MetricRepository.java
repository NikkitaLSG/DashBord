package org.example.vkr3test.Metrics;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface MetricRepository extends JpaRepository<Metric, Long> {
    List<Metric> findByTypeAndTimestampBetween(String type, LocalDateTime from, LocalDateTime to);
}