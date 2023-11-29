package com.app.springfx.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "computer_center")
public class ComputerCenter {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "computerCenter")
    @ToString.Exclude
    private List<ComputerDevice> computerDevices;

    @OneToMany(mappedBy = "computerCenter")
    @ToString.Exclude
    private List<Peripheral> peripherals;

    @OneToMany(mappedBy = "computerCenter")
    @ToString.Exclude
    private List<Technician> technicians;

}
