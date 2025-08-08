package com.ctci.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "competitions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"registrations", "awards", "notificationRequests"})
public class Competition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompetitionModality modality;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShooterCategory category;

    private String description;

    @Column(columnDefinition = "TEXT")
    private String rules;

    // Important dates
    @Column(name = "registration_open_date", nullable = false)
    private LocalDateTime registrationOpenDate;

    @Column(name = "registration_close_date", nullable = false)
    private LocalDateTime registrationCloseDate;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Column(name = "payment_deadline", nullable = false)
    private LocalDateTime paymentDeadline;

    // Financial information
    @Column(name = "registration_fee", nullable = false, precision = 10, scale = 2)
    private BigDecimal registrationFee;

    @Column(name = "total_prize", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrize;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "prize_distribution")
    private Map<Integer, BigDecimal> prizeDistribution;

    // Location and logistics
    @Column(name = "event_location")
    private String eventLocation;

    @Column(name = "max_participants")
    private Integer maxParticipants;

    @Builder.Default
    @Column(name = "current_participants")
    private Integer currentParticipants = 0;

    // Banking information
    @Column(name = "pix_key")
    private String pixKey;

    @Column(name = "bank_account")
    private String bankAccount;

    // Status and control
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private CompetitionStatus status = CompetitionStatus.PLANNED;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Registration> registrations;

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Award> awards;

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<NotificationRequest> notificationRequests;
}
