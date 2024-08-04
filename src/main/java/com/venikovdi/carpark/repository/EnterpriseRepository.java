package com.venikovdi.carpark.repository;

import com.venikovdi.carpark.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer> {

    boolean existsByTitle(String title);
}
