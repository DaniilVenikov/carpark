package com.venikovdi.carpark.repository;

import com.venikovdi.carpark.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Optional<Manager> findByUsername(String username);
}
