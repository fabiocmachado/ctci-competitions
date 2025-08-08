package com.ctci.domain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "awards",
        uniqueConstraints = @UniqueConstraint(columnNames = {"competition_id", "position"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"competition", "shooter", "registration"})
public class Award {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shooter_id", nullable = false)
    private Shooter shooter;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registration_id", nullable = false)
    private Registration registration;

    // Competition result
    @Column(nullable = false)
    private Integer position;

    @Column(precision = 8, scale = 2)
    private BigDecimal score;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "series_details")
    private Map<String, Object> seriesDetails;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "records_broken")
    private List<String> recordsBroken;

    // Financial award
    @Column(name = "prize_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal prizeAmount;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "prize_payment_status")
    private AwardPaymentStatus prizePaymentStatus = AwardPaymentStatus.PENDING;

    @Column(name = "prize_payment_date")
    private LocalDateTime prizePaymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @CreationTimestamp
    @Column(name = "result_date")
    private LocalDateTime resultDate;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
