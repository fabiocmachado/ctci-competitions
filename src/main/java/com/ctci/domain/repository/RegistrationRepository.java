package com.ctci.domain.repository;

import com.ctci.domain.model.PaymentStatus;
import com.ctci.domain.model.Registration;
import com.ctci.domain.model.RegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {

    List<Registration> findByShooterId(Long shooterId);
    List<Registration> findByCompetitionId(Long competitionId);
    List<Registration> findByStatus(RegistrationStatus status);
    List<Registration> findByPaymentStatus(PaymentStatus paymentStatus);


    List<Registration> findByCompetitionIdAndStatus(Long competitionId, RegistrationStatus status);
    List<Registration> findByShooterIdAndStatus(Long shooterId, RegistrationStatus status);

    Optional<Registration> findByShooterIdAndCompetitionId(Long shooterId, Long competitionId);

    List<Registration> findByPaymentStatusAndPaymentDateBefore(
            PaymentStatus paymentStatus,
            LocalDateTime deadline);

    @Query("SELECT r FROM Registration r WHERE r.paymentStatus = 'PENDING' AND " +
            "r.competition.paymentDeadline BETWEEN :now AND :warningTime")
    List<Registration> findRegistrationsWithUpcomingPaymentDeadline(
            @Param("now") LocalDateTime now,
            @Param("warningTime") LocalDateTime warningTime);

    @Query("SELECT r FROM Registration r WHERE r.paymentStatus = 'PENDING' AND " +
            "r.competition.paymentDeadline < :now")
    List<Registration> findOverdueRegistrations(@Param("now") LocalDateTime now);

    @Query("SELECT r FROM Registration r WHERE r.competition.id = :competitionId AND " +
            "r.status = 'CONFIRMED' ORDER BY r.registeredAt")
    List<Registration> findConfirmedRegistrationsByCompetition(@Param("competitionId") Long competitionId);

    @Query("SELECT COUNT(r) FROM Registration r WHERE r.competition.id = :competitionId AND " +
            "r.status = 'CONFIRMED'")
    long countConfirmedRegistrationsByCompetition(@Param("competitionId") Long competitionId);

    @Query("SELECT MAX(r.queuePosition) FROM Registration r WHERE r.competition.id = :competitionId")
    Optional<Integer> findMaxQueuePositionByCompetition(@Param("competitionId") Long competitionId);

    @Query("SELECT r.registeredCategory, COUNT(r) FROM Registration r WHERE r.status = 'CONFIRMED' " +
            "GROUP BY r.registeredCategory")
    List<Object[]> countConfirmedRegistrationsByCategory();

    @Query("SELECT DATE(r.registeredAt), COUNT(r) FROM Registration r WHERE " +
            "r.registeredAt >= :startDate GROUP BY DATE(r.registeredAt) ORDER BY DATE(r.registeredAt)")
    List<Object[]> countRegistrationsByDate(@Param("startDate") LocalDateTime startDate);
}
