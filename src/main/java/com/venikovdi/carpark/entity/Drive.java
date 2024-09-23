package com.venikovdi.carpark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(schema = "carpark", name = "tbl_drive")
@Entity
@Getter
@Setter
public class Drive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drive_id")
    private Long id;

    @Column(name = "start_drive")
    private LocalDateTime start;

    @Column(name = "end_drive")
    private LocalDateTime end;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
