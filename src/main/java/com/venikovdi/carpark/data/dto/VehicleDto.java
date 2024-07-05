package com.venikovdi.carpark.data.dto;

public record VehicleDto(

        Integer id,
        Integer price,
        Integer mileage,
        Integer releaseYear,
        String color,
        String number,
        Integer brandId
) {
}
