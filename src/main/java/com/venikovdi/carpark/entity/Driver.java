package com.venikovdi.carpark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(schema = "carpark", name = "tbl_driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "driver_id")
    private Integer id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "is_active")
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "enterprise_id")
    private Enterprise enterprise;

    @ManyToMany(mappedBy = "drivers")
    private Set<Vehicle> vehicles;
}
