package com.venikovdi.carpark.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

import java.time.LocalDateTime;

public record GPSResponseData(
        @JsonProperty("vehicle_id")
        @NonNull Integer vehicleId,
        @JsonProperty("timestamp")
        @NonNull LocalDateTime timestamp,
        @JsonProperty("latitude")
        @NonNull Double latitude,
        @JsonProperty("longitude")
        @NonNull Double longitude
) {
}
