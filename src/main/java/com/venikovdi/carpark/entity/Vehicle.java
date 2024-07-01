package com.venikovdi.carpark.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "carpark", name = "tbl_vehicle")
public class Vehicle {

    @Id
    @Column(name = "vehicle_id")
    private Integer id;

    @Column(name = "price")
    private Integer price;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "color")
    private String color;
}
