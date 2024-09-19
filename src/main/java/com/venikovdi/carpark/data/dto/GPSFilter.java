package com.venikovdi.carpark.data.dto;

import lombok.NonNull;

import java.time.OffsetDateTime;

public record GPSFilter(
        @NonNull OffsetDateTime rangeStart,
        @NonNull OffsetDateTime rangeEnd,
        boolean geoJSON
) {
}
