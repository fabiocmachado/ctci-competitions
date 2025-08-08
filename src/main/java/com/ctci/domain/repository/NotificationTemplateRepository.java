package com.ctci.domain.repository;

import com.ctci.domain.model.NotificationChannel;
import com.ctci.domain.model.NotificationPriority;
import com.ctci.domain.model.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {

    Optional<NotificationTemplate> findByCode(String code);
    List<NotificationTemplate> findByActiveTrue();
    List<NotificationTemplate> findByPriority(NotificationPriority priority);

    @Query(value = "SELECT * FROM notification_templates nt WHERE JSON_CONTAINS(nt.channel_types, JSON_QUOTE(:channel)) AND nt.active = true",
            nativeQuery = true)
    List<NotificationTemplate> findByChannelType(@Param("channel") String channel);

    @Query(value = "SELECT * FROM notification_templates nt WHERE JSON_CONTAINS(nt.available_variables, JSON_QUOTE(:variable))",
            nativeQuery = true)
    List<NotificationTemplate> findByAvailableVariable(@Param("variable") String variable);


    @Query("SELECT nt.code, COUNT(nr) FROM NotificationTemplate nt LEFT JOIN nt.notificationRequests nr " +
            "GROUP BY nt.id, nt.code ORDER BY COUNT(nr) DESC")
    List<Object[]> findTemplateUsageStatistics();
}