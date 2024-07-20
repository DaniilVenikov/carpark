package com.venikovdi.carpark.repository;

import com.venikovdi.carpark.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
}
