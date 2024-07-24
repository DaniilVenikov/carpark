package com.venikovdi.carpark.service;

import com.venikovdi.carpark.data.dto.DriverDto;
import com.venikovdi.carpark.entity.Manager;
import com.venikovdi.carpark.mapper.DriverToDriverDtoMapper;
import com.venikovdi.carpark.repository.DriverRepository;
import com.venikovdi.carpark.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final ManagerRepository managerRepository;
    private final DriverToDriverDtoMapper driverToDriverDtoMapper;

    public Collection<DriverDto> getAllDrivers() {
        return driverRepository.findAll()
                .stream()
                .map(driverToDriverDtoMapper::map)
                .toList();
    }

    public List<DriverDto> getAllDriversForManager(String username) {
        Manager manager = managerRepository.findByUsername(username).orElseThrow();

        return manager
                .getEnterprises()
                .stream()
                .flatMap(enterprise -> enterprise.getDrivers()
                        .stream()
                        .map(driverToDriverDtoMapper::map))
                .toList();
    }
}
