package com.venikovdi.carpark.controller;

import com.venikovdi.carpark.api.VehicleApi;
import com.venikovdi.carpark.api.data.VehicleResponseData;
import com.venikovdi.carpark.mapper.VehicleDtoToVehicleResponseDataMapper;
import com.venikovdi.carpark.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class VehicleController implements VehicleApi {

    private final VehicleService vehicleService;
    private final VehicleDtoToVehicleResponseDataMapper vehicleDtoToVehicleResponseDataMapper;

    @Override
    public ResponseEntity<Collection<VehicleResponseData>> get() {
        return ResponseEntity.ok(
                vehicleService.getAll()
                .stream()
                .map(vehicleDtoToVehicleResponseDataMapper::map)
                .toList());
    }
}
