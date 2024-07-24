package com.venikovdi.carpark.controller;

import com.venikovdi.carpark.api.VehicleApi;
import com.venikovdi.carpark.api.data.VehicleResponseData;
import com.venikovdi.carpark.mapper.VehicleDtoToVehicleResponseDataMapper;
import com.venikovdi.carpark.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class VehicleController implements VehicleApi {

    private final VehicleService vehicleService;
    private final VehicleDtoToVehicleResponseDataMapper vehicleDtoToVehicleResponseDataMapper;

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Collection<VehicleResponseData>> get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(
                vehicleService.getAllVehiclesForManager(username)
                .stream()
                .map(vehicleDtoToVehicleResponseDataMapper::map)
                .toList());
    }
}
