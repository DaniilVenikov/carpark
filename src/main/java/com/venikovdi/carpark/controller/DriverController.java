package com.venikovdi.carpark.controller;

import com.venikovdi.carpark.api.DriverApi;
import com.venikovdi.carpark.api.data.DriverResponseData;
import com.venikovdi.carpark.mapper.DriverDtoToDriverResponseDataMapper;
import com.venikovdi.carpark.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class DriverController implements DriverApi {

    private final DriverService driverService;
    private final DriverDtoToDriverResponseDataMapper driverDtoToDriverResponseDataMapper;

    @Override
    public ResponseEntity<Collection<DriverResponseData>> get() {
        return ResponseEntity.ok(
                driverService.getAllDrivers()
                .stream()
                .map(driverDtoToDriverResponseDataMapper::map)
                .toList()
        );
    }
}
