package com.app.springfx.domain;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "maintenance_log")
public class MaintenanceLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "computer_device_id")
    private ComputerDevice computerDevice;

    @ManyToOne
    @JoinColumn(name = "peripheral_id")
    private Peripheral peripheral;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Technician technician;
}
