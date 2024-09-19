package com.venikovdi.carpark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Entity
@Table(schema = "carpark", name = "tbl_gps")
@Getter
@Setter
public class GPS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id")
    private Vehicle vehicle;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    @Column(name = "geom", columnDefinition = "geometry(Point,4326)")
    private Point geom;
}
