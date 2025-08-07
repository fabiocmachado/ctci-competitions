package com.ctci.domain.model;

import jakarta.persistence.*;
import jakarta.servlet.Registration;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "shooters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"registrations", "awards", "notificationRequests"})
public class Shooter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(name = "push_token", length = 500)
    private String pushToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShooterCategory category;

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "origin_club")
    private String originClub;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> specialties;

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
    @OneToMany(mappedBy = "shooter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Registration> registrations;

    @OneToMany(mappedBy = "shooter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Award> awards;

    @OneToMany(mappedBy = "shooter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<NotificationRequest> notificationRequests;
}

