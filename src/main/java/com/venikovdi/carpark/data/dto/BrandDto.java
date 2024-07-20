package com.venikovdi.carpark.data.dto;

import com.venikovdi.carpark.entity.en.VehicleType;

public record BrandDto(
        String title,
        VehicleType type,
        Integer volumeTank,
        Integer liftingCapacity,
        Integer numberSeats
) {
}
