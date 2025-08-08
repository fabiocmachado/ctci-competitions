package com.ctci.domain.repository;

import com.ctci.domain.model.Award;
import com.ctci.domain.model.AwardPaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {

    List<Award> findByCompetitionId(Long competitionId);

    List<Award> findByShooterId(Long shooterId);

    List<Award> findByPrizePaymentStatus(AwardPaymentStatus paymentStatus);

    List<Award> findByCompetitionIdOrderByPosition(Long competitionId);

    Optional<Award> findByCompetitionIdAndPosition(Long competitionId, Integer position);

    @Query("SELECT a FROM Award a WHERE a.position <= :topPositions ORDER BY a.resultDate DESC")
    List<Award> findTopPerformers(@Param("topPositions") int topPositions);

    @Query("SELECT a FROM Award a WHERE a.shooter.id = :shooterId ORDER BY a.resultDate DESC")
    List<Award> findShooterAwardsOrderByDate(@Param("shooterId") Long shooterId);

    List<Award> findByPrizePaymentStatusAndResultDateBefore(
            AwardPaymentStatus paymentStatus,
            LocalDateTime deadline);

    @Query("SELECT SUM(a.prizeAmount) FROM Award a WHERE a.prizePaymentStatus = 'PENDING'")
    Optional<java.math.BigDecimal> calculateTotalPendingPrizeAmount();

    @Query("SELECT SUM(a.prizeAmount) FROM Award a WHERE a.competition.id = :competitionId")
    Optional<java.math.BigDecimal> calculateTotalPrizeAmountByCompetition(@Param("competitionId") Long competitionId);

    @Query("SELECT a FROM Award a WHERE SIZE(a.recordsBroken) > 0 ORDER BY a.resultDate DESC")
    List<Award> findAwardsWithRecords();

    @Query("SELECT a.shooter.name, COUNT(a) FROM Award a GROUP BY a.shooter.id, a.shooter.name " +
            "ORDER BY COUNT(a) DESC")
    List<Object[]> findTopShootersByAwardCount();

    @Query("SELECT a.competition.modality, AVG(a.score) FROM Award a WHERE a.score IS NOT NULL " +
            "GROUP BY a.competition.modality")
    List<Object[]> findAverageScoresByModality();
}
