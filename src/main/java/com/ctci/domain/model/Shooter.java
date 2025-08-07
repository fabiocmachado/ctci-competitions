package com.ctci.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "shooters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shooter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Column(name = "origin_club")
    private String originClub;

    @ElementCollection(targetClass = Specialty.class)
    @CollectionTable(name = "shooter_specialties", joinColumns = @JoinColumn(name = "shooter_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "specialty")
    private List<Specialty> specialties;

    @Enumerated(EnumType.STRING)
    private ShooterCategory category;

    @Enumerated(EnumType.STRING)
    private ShooterStatus status;
}
