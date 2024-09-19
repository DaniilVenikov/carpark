package com.venikovdi.carpark.data.dto;

import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

public record GPSDto(
        Integer vehicleId,
        LocalDateTime timestamp,
        Point geom
) {
}
