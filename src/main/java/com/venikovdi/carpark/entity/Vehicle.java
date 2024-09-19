package com.venikovdi.carpark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
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

    @Column(name = "purchase_datetime;")
    private ZonedDateTime purchaseDatetime;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @ManyToMany(cascade = {MERGE, PERSIST})
    @JoinTable(schema = "carpark", name = "tbl_driver_vehicle",
            joinColumns = @JoinColumn(name = "vehicle_id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id"))
    private Set<Driver> drivers;

    @OneToMany(mappedBy = "vehicle")
    private List<GPS> gps;
}
