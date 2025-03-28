package com.venikovdi.carpark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.util.Collection;
import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.CascadeType.MERGE;

@Getter
@Setter
@Entity
@Table(schema = "carpark", name = "tbl_enterprise")
public class Enterprise {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "enterprise_id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "city")
    private String city;

    @Column(name = "timezone")
    private ZoneId timezone;

    @OneToMany(mappedBy = "enterprise", cascade = MERGE)
    private Collection<Vehicle> vehicles;

    @OneToMany(mappedBy = "enterprise", cascade = MERGE)
    private Collection<Driver> drivers;

    @ManyToMany(mappedBy = "enterprises")
    private Set<Manager> managers;
}
