package com.venikovdi.carpark.api;

import com.venikovdi.carpark.api.data.GPSFilterData;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping(GPSApi.ROOT_PATH)
public interface GPSApi {

    String ROOT_PATH = Api.ROOT_PATH + "/gps";

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
    @GetMapping("{id}")
    ResponseEntity<?> get(
            @PathVariable(name = "id")
            Integer id,
            @Valid GPSFilterData gpsFilterData
    );
}
