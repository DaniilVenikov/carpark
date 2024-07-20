package com.venikovdi.carpark.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

public record DriverResponseData(
        @JsonProperty("full_name")
        @NonNull String fullName,
        @JsonProperty("salary")
        @NonNull Integer salary,
        @JsonProperty("enterprise_id")
        Integer enterpriseId
) {
}
