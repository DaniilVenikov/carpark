package com.venikovdi.carpark.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VehiclePutRequestData(
        @JsonProperty("price")
        Integer price,
        @JsonProperty("mileage")
        Integer mileage,
        @JsonProperty("release_year")
        Integer releaseYear,
        @JsonProperty("color")
        String color,
        @JsonProperty("number")
        String number,
        @JsonProperty("brand_id")
        Integer brandId,
        @JsonProperty("enterprise_id")
        Integer enterpriseId
) {
}
