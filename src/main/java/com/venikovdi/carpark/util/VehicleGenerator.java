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
        List<Vehicle> vehicles = enterpriseIds.stream()
                .map(id -> generateVehiclesForEnterprise(id, vehiclesCount))
                .flatMap(List::stream)
                .toList();

        vehicleRepository.saveAll(vehicles);
    }

    private List<Vehicle> generateVehiclesForEnterprise(int enterpriseId, int vehiclesCount) {
        List<Vehicle> vehicles = new ArrayList<>();

        enterpriseRepository.findById(enterpriseId)
                .ifPresent(enterprise -> {
                    List<Driver> drivers = new ArrayList<>(enterprise.getDrivers());

                    for (int i = 0; i < (vehiclesCount > 0 ? vehiclesCount : 1); i++) {
                        Vehicle vehicle = generateOneVehicle(enterprise);

                        if (!drivers.isEmpty() && (i != 0 && i % 10 == 0)) {
                            vehicle.setDrivers(Set.of(drivers.get(0)));
                        }

                        vehicles.add(vehicle);
                    }
                });

        return vehicles;
    }

    private Vehicle generateOneVehicle(Enterprise enterprise) {
        Vehicle vehicle = enhancedRandom.nextObject(Vehicle.class, "id", "brand", "enterprise", "drivers", "number");
        vehicle.setEnterprise(enterprise);
        return vehicle;
    }

}
