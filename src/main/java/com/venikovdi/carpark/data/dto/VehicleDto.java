package com.venikovdi.carpark.data.dto;

public record VehicleDto(
        Integer price,
        Integer mileage,
        Integer releaseYear,
        String color,
        String number,
        String brandTitle
) {
}
