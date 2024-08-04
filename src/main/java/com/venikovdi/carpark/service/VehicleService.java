package com.venikovdi.carpark.service;

import com.venikovdi.carpark.data.dto.VehicleDto;
import com.venikovdi.carpark.entity.Manager;
import com.venikovdi.carpark.entity.Vehicle;
import com.venikovdi.carpark.mapper.VehicleDtoToVehicleMapper;
import com.venikovdi.carpark.mapper.VehicleToVehicleDtoMapper;
import com.venikovdi.carpark.repository.ManagerRepository;
import com.venikovdi.carpark.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ManagerRepository managerRepository;
    private final VehicleToVehicleDtoMapper vehicleToVehicleDtoMapper;
    private final VehicleDtoToVehicleMapper vehicleDtoToVehicleMapper;

    public List<VehicleDto> getAll() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleToVehicleDtoMapper::map)
                .toList();
    }

    public List<VehicleDto> getAllVehiclesForManager(String username) {
        Manager manager = managerRepository.findByUsername(username).orElseThrow();

        return manager
                .getEnterprises()
                .stream()
                .flatMap(enterprise -> enterprise.getVehicles()
                        .stream()
                        .map(vehicleToVehicleDtoMapper::map))
                .toList();
    }

    @Transactional
    public VehicleDto add(VehicleDto vehicleDto) {
        var vehicleEntity = vehicleDtoToVehicleMapper.map(vehicleDto);
        return vehicleToVehicleDtoMapper.map(vehicleRepository.save(vehicleEntity));
    }

    @Transactional
    public Optional<VehicleDto> removeById(Integer id) {
        var vehicle = vehicleRepository.findById(id);
        if (vehicle.isEmpty()) {
            return Optional.empty();
        } else {
            vehicleRepository.delete(vehicle.get());
            return vehicle.map(vehicleToVehicleDtoMapper::map);
        }
    }

    @Transactional
    public Optional<VehicleDto> update(Integer id, VehicleDto vehicleDto) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if (vehicle.isEmpty()){
            return Optional.empty();
        }

        var vehicleUpdate = vehicleDtoToVehicleMapper.update(vehicle.get(), vehicleDto);
        return Optional.of(vehicleToVehicleDtoMapper.map(vehicleRepository.save(vehicleUpdate)));
    }
}
