package com.dnproject.platform.domain;

import com.dnproject.platform.domain.constant.AdoptionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "adoptions")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Animal animal;

    @Enumerated(EnumType.STRING)
    private AdoptionStatus status;
}
