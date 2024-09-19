package com.venikovdi.carpark.data.dto;

import java.time.ZonedDateTime;
import java.util.List;

public record VehicleDto(
        Integer id,
        Integer price,
        Integer mileage,
        Integer releaseYear,
        String color,
        String number,
        ZonedDateTime purchaseDatetime,
        Integer brandId,
        Integer enterpriseId,
        List<Integer> drivers
) {
}
