package com.venikovdi.carpark.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EnterprisePutRequestData(
        @JsonProperty("title")
        String title,
        @JsonProperty("city")
        String city
) {
}
