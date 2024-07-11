package com.venikovdi.carpark.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

public record BrandResponseData(
        @JsonProperty("title")
        @NonNull String title,
        @JsonProperty("type")
        @NonNull String type,
        @JsonProperty("volume_tank")
        @NonNull Integer volumeTank,
        @JsonProperty("lifting_capacity")
        @NonNull Integer liftingCapacity,
        @JsonProperty("number_seats")
        @NonNull Integer numberSeats
) {
}
