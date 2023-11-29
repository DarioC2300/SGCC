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
@Table(name = "technician")
public class Technician {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "center_id")
    private ComputerCenter computerCenter;

    @OneToMany(mappedBy = "technician")
    @ToString.Exclude
    private List<MaintenanceLog> maintenanceLogs;

    @Override
    public String toString(){
        return name;
    }
}
