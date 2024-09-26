package com.venikovdi.carpark.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

import java.time.LocalDateTime;

public record DriveResponseData(
        @JsonProperty("starting_Time")
        @NonNull LocalDateTime startingTime,
        @JsonProperty("end_Time")
        @NonNull LocalDateTime endTime,
        @JsonProperty("starting_latitude")
        @NonNull Double startingLatitude,
        @JsonProperty("starting_longitude")
        @NonNull Double startingLongitude,
        @JsonProperty("end_latitude")
        @NonNull Double endLatitude,
        @JsonProperty("end_longitude")
        @NonNull Double endLongitude,
        @JsonProperty("starting_address")
        String startingAddress,
        @JsonProperty("end_address")
        String endAddress
) {
}
