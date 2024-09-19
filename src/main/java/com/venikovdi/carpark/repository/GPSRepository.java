package com.venikovdi.carpark.repository;

import com.venikovdi.carpark.entity.GPS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface GPSRepository extends JpaRepository<GPS, Integer> {
    @Query("""
            FROM GPS gps
            WHERE gps.vehicle.id = :vehicleId
            AND gps.timestamp BETWEEN :startTimestamp AND :endTimestamp
            ORDER BY gps.timestamp ASC
            """)
    List<GPS> getGPS(Integer vehicleId, LocalDateTime startTimestamp, LocalDateTime endTimestamp);
}
