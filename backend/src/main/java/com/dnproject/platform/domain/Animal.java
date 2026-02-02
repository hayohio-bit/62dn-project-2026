package com.dnproject.platform.domain;

import com.dnproject.platform.domain.constant.AnimalStatus;
import com.dnproject.platform.domain.constant.Species;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "animals")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Species species;

    private String breed;

    @Enumerated(EnumType.STRING)
    private AnimalStatus status;
}
