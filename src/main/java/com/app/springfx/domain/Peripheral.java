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
@Table(name = "peripheral")
public class Peripheral {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String identificationKey;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "computer_Center_id")
    private ComputerCenter computerCenter;

    @OneToMany(mappedBy = "peripheral")
    @ToString.Exclude
    private List<MaintenanceLog> maintenanceLogs;

    @Column(nullable = false)
    private LocalDate adquisitionDate;

    @Override
    public String toString(){
        return identificationKey;
    }

}
