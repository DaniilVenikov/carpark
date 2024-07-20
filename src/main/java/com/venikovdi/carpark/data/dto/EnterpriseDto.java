package com.venikovdi.carpark.data.dto;

import java.util.List;

public record EnterpriseDto(
        String title,
        String city,
        List<Integer> vehicles
) {
}
