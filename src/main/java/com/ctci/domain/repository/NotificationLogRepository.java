package com.ctci.domain.repository;

import com.ctci.domain.model.LogLevel;
import com.ctci.domain.model.NotificationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {

    List<NotificationLog> findByLevel(LogLevel level);
    List<NotificationLog> findByCategory(String category);
    List<NotificationLog> findByExecutionId(Long executionId);
    List<NotificationLog> findByRequestId(Long requestId);

    List<NotificationLog> findByLoggedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT nl FROM NotificationLog nl WHERE nl.level IN ('ERROR', 'WARN') " +
            "ORDER BY nl.loggedAt DESC")
    List<NotificationLog> findErrorsAndWarnings();

    @Query("SELECT nl FROM NotificationLog nl WHERE nl.loggedAt >= :since ORDER BY nl.loggedAt DESC")
    Page<NotificationLog> findRecentLogs(@Param("since") LocalDateTime since, Pageable pageable);

    @Query("SELECT nl.level, COUNT(nl) FROM NotificationLog nl WHERE nl.loggedAt >= :startDate " +
            "GROUP BY nl.level ORDER BY COUNT(nl) DESC")
    List<Object[]> countLogsByLevel(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT nl.category, COUNT(nl) FROM NotificationLog nl WHERE nl.loggedAt >= :startDate " +
            "GROUP BY nl.category ORDER BY COUNT(nl) DESC")
    List<Object[]> countLogsByCategory(@Param("startDate") LocalDateTime startDate);

    @Modifying
    @Transactional
    @Query("DELETE FROM NotificationLog nl WHERE nl.loggedAt < :cutoffDate")
    void deleteLogsOlderThan(@Param("cutoffDate") LocalDateTime cutoffDate);
}