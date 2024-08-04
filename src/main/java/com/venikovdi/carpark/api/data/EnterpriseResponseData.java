package com.venikovdi.carpark.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

import java.util.List;

public record EnterpriseResponseData(
        @JsonProperty("id")
        @NonNull String id,
        @JsonProperty("title")
        @NonNull String title,
        @JsonProperty("city")
        @NonNull String city,
        @JsonProperty("vehicles_id")
        List<Integer> vehicles
) {
}
