package com.venikovdi.carpark.util;

import com.venikovdi.carpark.entity.Driver;
import com.venikovdi.carpark.entity.Enterprise;
import com.venikovdi.carpark.entity.Vehicle;
import com.venikovdi.carpark.repository.DriverRepository;
import com.venikovdi.carpark.repository.EnterpriseRepository;
import com.venikovdi.carpark.repository.VehicleRepository;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

@ShellComponent
public class VehicleGenerator {

    private final VehicleRepository vehicleRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final DriverRepository driverRepository;
    private final EnhancedRandom enhancedRandom;

    public VehicleGenerator(VehicleRepository vehicleRepository,
                            EnterpriseRepository enterpriseRepository,
                            DriverRepository driverRepository) {
        this.vehicleRepository = vehicleRepository;
        this.enterpriseRepository = enterpriseRepository;
        this.driverRepository = driverRepository;
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

    private Predicate<Field> priceField() {
        return field -> field.getName().equals("price") && field.getDeclaringClass().equals(Vehicle.class);
    }

    private Predicate<Field> mileageField() {
        return field -> field.getName().equals("mileage") && field.getDeclaringClass().equals(Vehicle.class);
    }

    private Predicate<Field> releaseYearField() {
        return field -> field.getName().equals("releaseYear") && field.getDeclaringClass().equals(Vehicle.class);
    }
    private Predicate<Field> colorField() {
        return field -> field.getName().equals("color") && field.getDeclaringClass().equals(Vehicle.class);
    }

    private Predicate<Field> numberField() {
        return field -> field.getName().equals("number") && field.getDeclaringClass().equals(Vehicle.class);
    }


    // Custom randomizer for color field
    public static class ColorRandomizer implements Randomizer<String> {
        private final String[] colors = {"белый", "синий", "красный", "чёрный"};
        private final Random random = new Random();

        @Override
        public String getRandomValue() {
            int index = random.nextInt(colors.length);
            return colors[index];
        }
    }

    private Predicate<Field> nameField() {
        return field -> field.getName().equals("fullName") && field.getDeclaringClass().equals(Driver.class);
    }

    private Predicate<Field> salaryField() {
        return field -> field.getName().equals("salary") && field.getDeclaringClass().equals(Driver.class);
    }

    public static class NameRandomizer implements Randomizer<String> {
        private final Random random = new Random();

        @Override
        public String getRandomValue() {
            String fullName = "Иванов Иван Иванович";
            return fullName + " " + random.nextInt(0, 2000000);
        }
    }

    public static class NumberPatternRandomizer implements Randomizer<String> {
        private final Random random = new Random();

        @Override
        public String getRandomValue() {
            char letter1 = randomLetter();
            char letter2 = randomLetter();
            char letter3 = randomLetter();

            int digits1 = randomDigits(3);
            int digits2 = randomDigits(2);
            int region = randomDigits(2) + 100 * (random.nextInt(10) + 1);  // 3 digits for region (including cases like 136)

            // Example: п241от42 or л001кк136
            return String.format("%c%03d%c%c%d", letter1, digits1, letter2, letter3, region);
        }

        private char randomLetter() {
            // русский алфавит
            String letters = "абвгдежзиклмнопрстуфхчшюя";
            return letters.charAt(random.nextInt(letters.length()));
        }

        private int randomDigits(int length) {
            int minValue = (int) Math.pow(10, length - 1);
            int maxValue = (int) Math.pow(10, length) - 1;
            return random.nextInt(maxValue - minValue + 1) + minValue;
        }
    }
}
