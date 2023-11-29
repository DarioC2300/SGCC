package com.app.springfx.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "software")
public class Software {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String version;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate installationDate;

    @Column(nullable = false)
    private String size;

    @ManyToMany(mappedBy = "softwares")
    List<ComputerDevice> computerDevices;

}