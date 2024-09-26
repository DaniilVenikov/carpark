package com.venikovdi.carpark.api;

import com.venikovdi.carpark.api.data.DriveResponseData;
import com.venikovdi.carpark.api.data.GPSResponseData;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.OffsetDateTime;
import java.util.Collection;

@Validated
@RequestMapping(DriveApi.ROOT_PATH)
public interface DriveApi {
    String ROOT_PATH = Api.ROOT_PATH + "/drive";

    @ApiResponse(responseCode = "200", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400", content = @Content(
            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
            schema = @Schema(implementation = ProblemDetail.class)
    ))
    @ApiResponse(responseCode = "403", content = @Content(
            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
            schema = @Schema(implementation = ProblemDetail.class)
    ))
    @ApiResponse(responseCode = "500", content = @Content(
            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
            schema = @Schema(implementation = ProblemDetail.class)
    ))
    @GetMapping("/gps/{vehicle_id}")
    ResponseEntity<Collection<GPSResponseData>> getDriveGps(
            @PathVariable(name = "vehicle_id", required = false)
            @NotNull Integer vehicleId,
            @RequestParam(name = "range_start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime rangeStart,
            @RequestParam(name = "range_end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime rangeEnd
    );

    @GetMapping("{vehicle_id}")
    ResponseEntity<Collection<DriveResponseData>> getDrive(
            @PathVariable(name = "vehicle_id", required = false)
            @NotNull Integer vehicleId,
            @RequestParam(name = "range_start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime rangeStart,
            @RequestParam(name = "range_end")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime rangeEnd
    );
}
