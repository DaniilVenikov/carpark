package com.venikovdi.carpark.api.data;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import java.beans.ConstructorProperties;
import java.time.OffsetDateTime;

public record GPSFilterData(
        @Parameter(name = RANGE_START_QUERY_PARAMETER)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        OffsetDateTime rangeStart,
        @Parameter(name = RANGE_END_QUERY_PARAMETER)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        OffsetDateTime rangeEnd,
        @Parameter(name = GEO_JSON_QUERY_PARAMETER)
        boolean geoJSON

) {
    public static final String RANGE_START_QUERY_PARAMETER = "range_start";
    public static final String RANGE_END_QUERY_PARAMETER = "range_end";
    public static final String GEO_JSON_QUERY_PARAMETER = "geo_JSON";

    @ConstructorProperties({
            RANGE_START_QUERY_PARAMETER,
            RANGE_END_QUERY_PARAMETER,
            GEO_JSON_QUERY_PARAMETER
    })
    public GPSFilterData{}
}
