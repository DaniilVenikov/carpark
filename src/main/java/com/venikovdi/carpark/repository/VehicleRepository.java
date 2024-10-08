package com.venikovdi.carpark.repository;

import com.venikovdi.carpark.entity.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer>, PagingAndSortingRepository<Vehicle, Integer> {

    Page<Vehicle> findAllByEnterpriseIdIn(List<Integer> enterpriseIds, Pageable pageable);

    List<Vehicle> findByEnterpriseId(Integer id);
}
