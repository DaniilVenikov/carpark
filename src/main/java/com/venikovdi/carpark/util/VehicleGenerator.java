package com.venikovdi.carpark.util;

import com.venikovdi.carpark.entity.Brand;
import com.venikovdi.carpark.entity.Driver;
import com.venikovdi.carpark.entity.Enterprise;
import com.venikovdi.carpark.entity.Vehicle;
import com.venikovdi.carpark.repository.BrandRepository;
import com.venikovdi.carpark.repository.DriverRepository;
import com.venikovdi.carpark.repository.EnterpriseRepository;
import com.venikovdi.carpark.repository.VehicleRepository;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.venikovdi.carpark.util.FieldGeneratorUtil.*;

@ShellComponent
public class VehicleGenerator {

    private final VehicleRepository vehicleRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final DriverRepository driverRepository;
    private final EnhancedRandom enhancedRandom;
    private final BrandRepository brandRepository;

    public VehicleGenerator(VehicleRepository vehicleRepository,
                            EnterpriseRepository enterpriseRepository,
                            DriverRepository driverRepository,
                            BrandRepository brandRepository) {
        this.vehicleRepository = vehicleRepository;
        this.enterpriseRepository = enterpriseRepository;
        this.driverRepository = driverRepository;
        this.brandRepository = brandRepository;
        enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .randomize(priceField(), new IntegerRangeRandomizer(100000, 50000000))
                .randomize(mileageField(), new IntegerRangeRandomizer(0, 300000))
                .randomize(releaseYearField(), new IntegerRangeRandomizer(2010, 2024))
                .randomize(colorField(), new ColorRandomizer())
                .randomize(nameField(), new NameRandomizer())
                .randomize(numberField(), new NumberPatternRandomizer())
                .randomize(salaryField(), new IntegerRangeRandomizer(50000, 150000))
                .build();
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
                    for (int i = 0; i < (vehiclesCount > 0 ? vehiclesCount : 1); i++) {
                        Vehicle vehicle = generateOneVehicle(enterprise);

                        if ((i != 0 && i % 10 == 0)) {
                            vehicle.setDrivers(Set.of(generateActiveDriver(enterprise, vehicle)));
                        }

                        vehicles.add(vehicle);
                    }
                });

        return vehicles;
    }

    private Vehicle generateOneVehicle(Enterprise enterprise) {
        Vehicle vehicle = enhancedRandom.nextObject(Vehicle.class, "id", "brand", "enterprise", "drivers");
        vehicle.setEnterprise(enterprise);

        Optional<Brand> brand = brandRepository.findById(getRandomBrandId());
        vehicle.setBrand(brand.orElseThrow());

        return vehicle;
    }

    private Driver generateActiveDriver(Enterprise enterprise, Vehicle vehicle) {
        Driver driver = enhancedRandom.nextObject(Driver.class, "isActive", "enterprise", "vehicles");
        driver.setIsActive(true);
        driver.setEnterprise(enterprise);
        driver.setVehicles(Set.of(vehicle));

        driver = driverRepository.save(driver);
        return driver;
    }
}
