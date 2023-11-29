package com.app.springfx.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "computer_device")
public class ComputerDevice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String identificationKey;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Double price;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "computer_device_software",
            joinColumns = @JoinColumn(name = "computer_device_id"),
            inverseJoinColumns = @JoinColumn(name = "software_id")
    )
    private List<Software> softwares;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private ComputerCenter computerCenter;

    @OneToMany(mappedBy = "computerDevice")
    @ToString.Exclude
    private List<MaintenanceLog> maintenanceLogs;

    @Column(nullable = false)
    private LocalDate adquisitionDate;
    @Override
    public String toString(){
        return name;
    }
}