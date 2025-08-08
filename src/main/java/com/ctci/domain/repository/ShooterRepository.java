package com.ctci.domain.repository;

import com.ctci.domain.model.Shooter;
import com.ctci.domain.model.ShooterCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShooterRepository extends JpaRepository<Shooter, Long> {

    Optional<Shooter> findByEmail(String email);

    List<Shooter> findByCategory(ShooterCategory category);

    List<Shooter> findByActiveTrue();

    List<Shooter> findByCategoryAndActiveTrue(ShooterCategory category);

    @Query(value = "SELECT * FROM shooters s WHERE JSON_CONTAINS(s.specialties, JSON_QUOTE(:specialty)) AND s.active = true",
            nativeQuery = true)
    List<Shooter> findBySpecialty(@Param("specialty") String specialty);

    @Query(value = "SELECT * FROM shooters s WHERE s.active = true AND " +
            "(:category IS NULL OR s.category = :category) AND " +
            "(:specialty IS NULL OR JSON_CONTAINS(s.specialties, JSON_QUOTE(:specialty)))",
            nativeQuery = true)
    List<Shooter> findActiveShootersByCategoryAndSpecialty(
            @Param("category") String category,  // Changed to String for native query
            @Param("specialty") String specialty);

    List<Shooter> findByPushTokenIsNotNullAndActiveTrue();

    @Query("SELECT s FROM Shooter s WHERE s.pushToken IS NOT NULL AND s.active = true AND " +
            "s.category = :category")
    List<Shooter> findActivePushTokenShootersByCategory(@Param("category") ShooterCategory category);

    @Query("SELECT COUNT(s) FROM Shooter s WHERE s.active = true")
    long countActiveShooters();

    @Query("SELECT s.category, COUNT(s) FROM Shooter s WHERE s.active = true GROUP BY s.category")
    List<Object[]> countShootersByCategory();

    @Query("SELECT s FROM Shooter s JOIN s.registrations r WHERE r.competition.id = :competitionId")
    List<Shooter> findShootersByCompetitionId(@Param("competitionId") Long competitionId);
}