package com.venikovdi.carpark.util;

import com.venikovdi.carpark.entity.Driver;
import com.venikovdi.carpark.entity.Vehicle;
import io.github.benas.randombeans.api.Randomizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class FieldGeneratorUtil {
    public static Predicate<Field> priceField() {
        return field -> field.getName().equals("price") && field.getDeclaringClass().equals(Vehicle.class);
    }

    public static Predicate<Field> mileageField() {
        return field -> field.getName().equals("mileage") && field.getDeclaringClass().equals(Vehicle.class);
    }

    public static Predicate<Field> releaseYearField() {
        return field -> field.getName().equals("releaseYear") && field.getDeclaringClass().equals(Vehicle.class);
    }
    public static Predicate<Field> colorField() {
        return field -> field.getName().equals("color") && field.getDeclaringClass().equals(Vehicle.class);
    }

    public static Predicate<Field> numberField() {
        return field -> field.getName().equals("number") && field.getDeclaringClass().equals(Vehicle.class);
    }

    public static Integer getRandomBrandId() {
        Random random = new Random();
        return random.nextInt(1, 5);
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



    public static Predicate<Field> nameField() {
        return field -> field.getName().equals("fullName") && field.getDeclaringClass().equals(Driver.class);
    }

    public static Predicate<Field> salaryField() {
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
            String letters = "авеикмнорстух";
            return letters.charAt(random.nextInt(letters.length()));
        }

        private int randomDigits(int length) {
            int minValue = (int) Math.pow(10, length - 1);
            int maxValue = (int) Math.pow(10, length) - 1;
            return random.nextInt(maxValue - minValue + 1) + minValue;
        }
    }
}
