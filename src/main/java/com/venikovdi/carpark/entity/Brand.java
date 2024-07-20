package com.venikovdi.carpark.entity;


import com.venikovdi.carpark.entity.en.VehicleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(schema = "carpark", name = "tbl_brand")
public class Brand {
    @Id
    @Column(name = "brand_id")
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @Column(name = "volume_tank")
    private Integer volumeTank;

    @Column(name = "lifting_capacity")
    private Integer liftingCapacity;

    @Column(name = "number_seats")
    private Integer numberSeats;

    @OneToMany(mappedBy = "brand", cascade = MERGE)
    private Collection<Vehicle> vehicles;
}
