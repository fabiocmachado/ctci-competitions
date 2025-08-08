package com.ctci.domain.repository;

import java.time.LocalDateTime;
import java.util.Map;

public interface NotificationStatisticsRepository {

    /**
     * Get comprehensive notification statistics for a date range
     */
    Map<String, Object> getNotificationStatistics(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Get shooter engagement metrics
     */
    Map<String, Object> getShooterEngagementMetrics(Long shooterId);

    /**
     * Get competition notification summary
     */
    Map<String, Object> getCompetitionNotificationSummary(Long competitionId);
}
