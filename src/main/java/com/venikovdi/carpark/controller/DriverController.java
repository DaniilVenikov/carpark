package com.venikovdi.carpark.controller;

import com.venikovdi.carpark.api.DriverApi;
import com.venikovdi.carpark.api.data.DriverResponseData;
import com.venikovdi.carpark.mapper.DriverDtoToDriverResponseDataMapper;
import com.venikovdi.carpark.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class DriverController implements DriverApi {

    private final DriverService driverService;
    private final DriverDtoToDriverResponseDataMapper driverDtoToDriverResponseDataMapper;

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Collection<DriverResponseData>> get() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(
                driverService.getAllDriversForManager(username)
                .stream()
                .map(driverDtoToDriverResponseDataMapper::map)
                .toList()
        );
    }
}
