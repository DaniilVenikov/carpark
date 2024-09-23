package com.venikovdi.carpark.data.dto;

import java.time.LocalDateTime;

public record DriveDto(
        LocalDateTime start,
        LocalDateTime end,
        Integer vehicleId
) {
}
