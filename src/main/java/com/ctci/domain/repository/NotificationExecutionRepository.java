package com.ctci.domain.repository;

import com.ctci.domain.model.ExecutionStatus;
import com.ctci.domain.model.NotificationChannel;
import com.ctci.domain.model.NotificationExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface NotificationExecutionRepository extends JpaRepository<NotificationExecution, Long> {

    List<NotificationExecution> findByRequestId(Long requestId);
    List<NotificationExecution> findByChannel(NotificationChannel channel);
    List<NotificationExecution> findByStatus(ExecutionStatus status);

    List<NotificationExecution> findByStatusAndAttempts(ExecutionStatus status, int maxAttempts);

    @Query("SELECT ne FROM NotificationExecution ne WHERE ne.status = 'FAILED' AND " +
            "ne.attempts < ne.maxAttempts")
    List<NotificationExecution> findFailedExecutionsForRetry();

    @Query("SELECT ne.providerUsed, ne.status, COUNT(ne) FROM NotificationExecution ne " +
            "GROUP BY ne.providerUsed, ne.status")
    List<Object[]> findExecutionStatsByProvider();

    @Query("SELECT ne.channel, " +
            "COUNT(CASE WHEN ne.status = 'SENT' THEN 1 END) as sent, " +
            "COUNT(ne) as total FROM NotificationExecution ne " +
            "WHERE ne.createdAt >= :startDate GROUP BY ne.channel")
    List<Object[]> calculateSuccessRateByChannel(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT ne FROM NotificationExecution ne WHERE ne.createdAt >= :since ORDER BY ne.createdAt DESC")
    List<NotificationExecution> findRecentExecutions(@Param("since") LocalDateTime since);

    @Query("SELECT ne.errorCode, COUNT(ne) FROM NotificationExecution ne WHERE ne.status = 'FAILED' " +
            "AND ne.errorCode IS NOT NULL GROUP BY ne.errorCode ORDER BY COUNT(ne) DESC")
    List<Object[]> findMostCommonErrors();
}
