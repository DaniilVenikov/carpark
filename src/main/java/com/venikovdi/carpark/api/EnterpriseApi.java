package com.venikovdi.carpark.api;

import com.venikovdi.carpark.api.data.EnterprisePostRequestData;
import com.venikovdi.carpark.api.data.EnterprisePutRequestData;
import com.venikovdi.carpark.api.data.EnterpriseResponseData;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Validated
@RequestMapping(EnterpriseApi.ROOT_PATH)
public interface EnterpriseApi {

    String ROOT_PATH = Api.ROOT_PATH + "/enterprise";


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
    ResponseEntity<Collection<EnterpriseResponseData>> get();

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
    ResponseEntity<EnterpriseResponseData> post(
            @RequestBody
            @NotNull @Valid
            EnterprisePostRequestData enterprise
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
    @PutMapping("/{id}")
    ResponseEntity<EnterpriseResponseData> put(
            @PathVariable(name = "id", required = false)
            @NotNull Integer id,
            @RequestBody
            @NotNull @Valid
            EnterprisePutRequestData enterprisePutRequestData
    );
}
