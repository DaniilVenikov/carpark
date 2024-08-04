package com.venikovdi.carpark.api.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record EnterprisePostRequestData(
        @JsonProperty("title")
        @NotNull
        String title,
        @JsonProperty("city")
        @NotNull
        String city
){
}
