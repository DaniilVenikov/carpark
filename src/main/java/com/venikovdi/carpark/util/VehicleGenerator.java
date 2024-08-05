package com.venikovdi.carpark.util;

import com.venikovdi.carpark.entity.Driver;
import com.venikovdi.carpark.entity.Enterprise;
import com.venikovdi.carpark.entity.Vehicle;
import com.venikovdi.carpark.repository.EnterpriseRepository;
import com.venikovdi.carpark.repository.VehicleRepository;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@ShellComponent
public class VehicleGenerator {

    private final VehicleRepository vehicleRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final EnhancedRandom enhancedRandom;

    public VehicleGenerator(VehicleRepository vehicleRepository,
                            EnterpriseRepository enterpriseRepository) {
        this.vehicleRepository = vehicleRepository;
        this.enterpriseRepository = enterpriseRepository;
        this.enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
    }

    @ShellMethod(key = "generate-vehicle")
    @Transactional
    public void generateVehicle(
            @Option(required = true) List<Integer> enterpriseIds,
            @ShellOption(defaultValue = "0") int vehiclesCount
    ) {
        List<Vehicle> vehicles =
                vehiclesCount == 0 ? generateVehicle(enterpriseIds) : generateListVehicle(enterpriseIds, vehiclesCount);
        vehicleRepository.saveAll(vehicles);

    }

    private List<Vehicle> generateListVehicle(List<Integer> enterpriseIds, int vehiclesCount) {
        List<Vehicle> vehicles = new ArrayList<>();
        for (int id : enterpriseIds) {
            Optional<Enterprise> enterprise = enterpriseRepository.findById(id);
            if (enterprise.isPresent()) {
                List<Driver> drivers = (List<Driver>) enterprise.get().getDrivers();

                for (int vehicleCount = 0; vehicleCount < vehiclesCount; vehicleCount++) {
                    Vehicle vehicle = generateOneVehicle(enterprise.get());

                    if (!drivers.isEmpty() && (vehicleCount != 0 && vehicleCount % 10 == 0)) {
                        vehicle.setDrivers(Set.of(drivers.get(0)));
                    }
                    vehicles.add(vehicle);
                }
            }
        }
        return vehicles;
    }

    private List<Vehicle> generateVehicle(List<Integer> enterpriseIds) {
        List<Vehicle> vehicles = new ArrayList<>();
        for (int id : enterpriseIds){
            Optional<Enterprise> enterprise = enterpriseRepository.findById(id);
            enterprise.ifPresent(value -> vehicles.add(generateOneVehicle(value)));
        }
        return vehicles;
    }

    private Vehicle generateOneVehicle(Enterprise enterprise) {
        Vehicle vehicle = enhancedRandom.nextObject(Vehicle.class, "id","brand", "enterprise", "drivers", "number");
        vehicle.setEnterprise(enterprise);

        return vehicle;
    }
}
