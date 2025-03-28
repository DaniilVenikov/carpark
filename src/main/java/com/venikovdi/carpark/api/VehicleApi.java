package com.venikovdi.carpark.api;

import com.venikovdi.carpark.api.data.VehiclePostRequestData;
import com.venikovdi.carpark.api.data.VehiclePutRequestData;
import com.venikovdi.carpark.api.data.VehicleResponseData;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping(VehicleApi.ROOT_PATH)
public interface VehicleApi {
    String ROOT_PATH = Api.ROOT_PATH + "/vehicle";

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
    @GetMapping
    ResponseEntity<Page<VehicleResponseData>> getAll(Pageable pageable);

    @ApiResponse(responseCode = "201", useReturnTypeSchema = true)
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
    @PostMapping
    ResponseEntity<VehicleResponseData> post(
            @RequestBody
            @NotNull @Valid
            VehiclePostRequestData vehicle
    );

    @ApiResponse(responseCode = "200", useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400",
            content = @Content(
                    mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                    schema = @Schema(implementation = ProblemDetail.class)
            ))
    @ApiResponse(responseCode = "403", content = @Content(
            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
            schema = @Schema(implementation = ProblemDetail.class)
    ))
    @ApiResponse(responseCode = "404",
            content = @Content(
                    mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                    schema = @Schema(implementation = ProblemDetail.class)
            ))
    @ApiResponse(responseCode = "500",
            content = @Content(
                    mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                    schema = @Schema(implementation = ProblemDetail.class)
            ))
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @PathVariable(name = "id", required = false)
            @NotNull Integer id
    );

    @PutMapping("/{id}")
    ResponseEntity<VehicleResponseData> put(
            @PathVariable(name = "id", required = false)
            @NotNull Integer id,
            @RequestBody
            @NotNull @Valid
            VehiclePutRequestData vehiclePutRequestData
    );
}
