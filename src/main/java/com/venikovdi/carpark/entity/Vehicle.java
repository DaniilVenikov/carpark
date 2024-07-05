package com.venikovdi.carpark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(schema = "carpark", name = "tbl_vehicle")
public class Vehicle {

    @Id
    @Column(name = "vehicle_id")
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(name = "price")
    private Integer price;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "color")
    private String color;

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
