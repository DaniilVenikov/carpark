package com.venikovdi.carpark.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

import java.util.List;

public record VehicleResponseData(
        @JsonProperty("price")
        @NonNull Integer price,
        @JsonProperty("mileage")
        @NonNull Integer mileage,
        @JsonProperty("release_year")
        @NonNull Integer releaseYear,
        @JsonProperty("color")
        @NonNull String color,
        @JsonProperty("number")
        @NonNull String number,
        @JsonProperty("brand_id")
        Integer brandId,
        @JsonProperty("enterprise_id")
        Integer enterpriseId,
        @JsonProperty("drivers")
        List<String> drivers
) {
}
