package com.venikovdi.carpark.data.dto;

import java.time.LocalDateTime;

public record DriveDto(
        LocalDateTime startingTime,
        LocalDateTime endTime,
        Integer vehicleId
) {
}
