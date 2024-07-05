package com.venikovdi.carpark.repository;

import com.venikovdi.carpark.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
}
