package com.app.springfx.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Assingment")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String identificationKey;

    @Column(nullable = false)
    private String nameProgram;


    public Assignment(String identificationKey, String name) {
        this.identificationKey = identificationKey;
        this.nameProgram = name;
    }
}
