package com.venikovdi.carpark.data.dto;

import java.util.List;

public record VehicleDto(
        Integer id,
        Integer price,
        Integer mileage,
        Integer releaseYear,
        String color,
        String number,
        Integer brandId,
        Integer enterpriseId,
        List<Integer> drivers
) {
}
