package com.venikovdi.carpark.controller;

import com.venikovdi.carpark.api.VehicleApi;
import com.venikovdi.carpark.api.data.VehiclePostRequestData;
import com.venikovdi.carpark.api.data.VehiclePutRequestData;
import com.venikovdi.carpark.api.data.VehicleResponseData;
import com.venikovdi.carpark.data.dto.VehicleDto;
import com.venikovdi.carpark.mapper.VehicleDtoToVehicleResponseDataMapper;
import com.venikovdi.carpark.mapper.VehiclePostRequestDataToVehicleDtoMapper;
import com.venikovdi.carpark.mapper.VehiclePutRequestDataToVehicleDtoMapper;
import com.venikovdi.carpark.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class VehicleController implements VehicleApi {

    private final VehicleService vehicleService;
    private final VehicleDtoToVehicleResponseDataMapper vehicleDtoToVehicleResponseDataMapper;
    private final VehiclePostRequestDataToVehicleDtoMapper vehiclePostRequestDataToVehicleDtoMapper;
    private final VehiclePutRequestDataToVehicleDtoMapper vehiclePutRequestDataToVehicleDtoMapper;

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Page<VehicleResponseData>> getAll(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(
                vehicleService.getAllVehiclesForManager(username,
                                PageRequest.of(
                                        pageable.getPageNumber(),
                                        pageable.getPageSize()
                                ))
                        .map(vehicleDtoToVehicleResponseDataMapper::map));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<VehicleResponseData> post(VehiclePostRequestData vehicle) {
        var responseData = vehicleService.add(vehiclePostRequestDataToVehicleDtoMapper.map(vehicle));
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleDtoToVehicleResponseDataMapper.map(responseData));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<Void> delete(Integer id) {
        Optional<VehicleDto> vehicleDto = vehicleService.removeById(id);
        if (vehicleDto.isPresent()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<VehicleResponseData> put(Integer id, VehiclePutRequestData vehiclePutRequestData) {
        Optional<VehicleDto> vehicleDto =
                vehicleService.update(id, vehiclePutRequestDataToVehicleDtoMapper.map(vehiclePutRequestData));
        return vehicleDto
                .map(dto -> ResponseEntity.ok(vehicleDtoToVehicleResponseDataMapper.map(dto)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
