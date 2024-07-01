package com.venikovdi.carpark.repository;

import com.venikovdi.carpark.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
}
