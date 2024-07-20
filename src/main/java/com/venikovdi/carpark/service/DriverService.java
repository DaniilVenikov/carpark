package com.venikovdi.carpark.service;

import com.venikovdi.carpark.data.dto.DriverDto;
import com.venikovdi.carpark.mapper.DriverToDriverDtoMapper;
import com.venikovdi.carpark.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverRepository driverRepository;
    private final DriverToDriverDtoMapper driverToDriverDtoMapper;

    public Collection<DriverDto> getAllDrivers() {
        return driverRepository.findAll()
                .stream()
                .map(driverToDriverDtoMapper::map)
                .toList();
    }
}
