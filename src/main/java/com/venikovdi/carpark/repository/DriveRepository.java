package com.venikovdi.carpark.repository;

import com.venikovdi.carpark.entity.Drive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DriveRepository extends JpaRepository<Drive, Long> {
    List<Drive> findAllByVehicleIdAndStartGreaterThanEqualAndEndLessThanEqual(Integer vehicleId,
                                                                              LocalDateTime rangeStart,
                                                                              LocalDateTime rangeEnd);
}
