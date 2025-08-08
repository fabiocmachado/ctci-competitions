package com.ctci.domain.repository;

import com.ctci.domain.model.Competition;
import com.ctci.domain.model.CompetitionModality;
import com.ctci.domain.model.CompetitionStatus;
import com.ctci.domain.model.ShooterCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    List<Competition> findByStatus(CompetitionStatus status);
    List<Competition> findByActiveTrue();
    List<Competition> findByModalityAndActiveTrue(CompetitionModality modality);
    List<Competition> findByCategoryAndActiveTrue(ShooterCategory category);

    List<Competition> findByEventDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Competition> findByEventDateAfter(LocalDateTime date);

    @Query("SELECT c FROM Competition c WHERE c.eventDate >= :startDate AND c.eventDate <= :endDate AND c.active = true")
    List<Competition> findActiveCompetitionsByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT c FROM Competition c WHERE c.registrationOpenDate <= :now AND " +
            "c.registrationCloseDate >= :now AND c.active = true")
    List<Competition> findCompetitionsWithOpenRegistration(@Param("now") LocalDateTime now);

    @Query("SELECT c FROM Competition c WHERE c.registrationCloseDate BETWEEN :now AND :deadline AND " +
            "c.status = 'REGISTRATION_OPEN'")
    List<Competition> findCompetitionsClosingSoon(
            @Param("now") LocalDateTime now,
            @Param("deadline") LocalDateTime deadline);

    @Query("SELECT c FROM Competition c WHERE c.paymentDeadline BETWEEN :now AND :deadline")
    List<Competition> findCompetitionsWithPaymentDeadlineSoon(
            @Param("now") LocalDateTime now,
            @Param("deadline") LocalDateTime deadline);

    @Query("SELECT c FROM Competition c WHERE c.currentParticipants < c.maxParticipants AND c.active = true")
    List<Competition> findCompetitionsWithAvailableSlots();

    @Query("SELECT c FROM Competition c WHERE c.maxParticipants - c.currentParticipants <= :threshold AND " +
            "c.status = 'REGISTRATION_OPEN'")
    List<Competition> findCompetitionsNearCapacity(@Param("threshold") int threshold);

    @Query("SELECT c.modality, COUNT(c) FROM Competition c WHERE c.active = true GROUP BY c.modality")
    List<Object[]> countCompetitionsByModality();

    @Query("SELECT c.status, COUNT(c) FROM Competition c GROUP BY c.status")
    List<Object[]> countCompetitionsByStatus();
}