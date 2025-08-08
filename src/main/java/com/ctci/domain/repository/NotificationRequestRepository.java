package com.ctci.domain.repository;

import com.ctci.domain.model.NotificationPriority;
import com.ctci.domain.model.NotificationRequest;
import com.ctci.domain.model.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRequestRepository extends JpaRepository<NotificationRequest, Long> {

    // Basic queries
    List<NotificationRequest> findByStatus(NotificationStatus status);
    List<NotificationRequest> findByShooterId(Long shooterId);
    List<NotificationRequest> findByTemplateId(Long templateId);
    List<NotificationRequest> findByPriority(NotificationPriority priority);

    // Scheduling queries
    @Query("SELECT nr FROM NotificationRequest nr WHERE nr.status = 'PENDING' AND nr.sendAt <= :now")
    List<NotificationRequest> findPendingNotificationsToSend(@Param("now") LocalDateTime now);

    @Query("SELECT nr FROM NotificationRequest nr WHERE nr.status = 'PENDING' AND " +
            "nr.sendAt BETWEEN :now AND :future ORDER BY nr.priority DESC, nr.sendAt ASC")
    List<NotificationRequest> findScheduledNotifications(
            @Param("now") LocalDateTime now,
            @Param("future") LocalDateTime future);

    List<NotificationRequest> findByCompetitionId(Long competitionId);
    List<NotificationRequest> findByRegistrationId(Long registrationId);

    @Query("SELECT nr FROM NotificationRequest nr WHERE nr.priority = 'HIGH' AND nr.status = 'PENDING'")
    List<NotificationRequest> findHighPriorityPendingNotifications();

    @Query("SELECT nr.status, COUNT(nr) FROM NotificationRequest nr GROUP BY nr.status")
    List<Object[]> countNotificationsByStatus();

    @Query("SELECT DATE(nr.createdAt), COUNT(nr) FROM NotificationRequest nr WHERE " +
            "nr.createdAt >= :startDate GROUP BY DATE(nr.createdAt) ORDER BY DATE(nr.createdAt)")
    List<Object[]> countNotificationsByDate(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT nr FROM NotificationRequest nr WHERE nr.status = 'FAILED' AND " +
            "SIZE(nr.executions) < nr.maxAttempts")
    List<NotificationRequest> findFailedNotificationsForRetry();
}