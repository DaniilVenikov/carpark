package com.venikovdi.carpark.service;

import com.venikovdi.carpark.data.dto.VehicleDto;
import com.venikovdi.carpark.mapper.VehicleToVehicleDtoMapper;
import com.venikovdi.carpark.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleToVehicleDtoMapper vehicleToVehicleDtoMapper;

    public List<VehicleDto> getAll() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleToVehicleDtoMapper::map)
                .toList();
    }
}
