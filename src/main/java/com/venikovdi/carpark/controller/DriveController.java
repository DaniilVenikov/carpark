package com.venikovdi.carpark.controller;

import com.venikovdi.carpark.api.DriveApi;
import com.venikovdi.carpark.api.data.GPSResponseData;
import com.venikovdi.carpark.mapper.GPSDtoToGPSResponseDataMapper;
import com.venikovdi.carpark.service.DriveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class DriveController implements DriveApi {
    private final DriveService driveService;
    private final GPSDtoToGPSResponseDataMapper gpsDtoToGPSResponseDataMapper;
    @Override
    public ResponseEntity<Collection<GPSResponseData>> getDrive(Integer vehicleId,
                                                                OffsetDateTime rangeStart,
                                                                OffsetDateTime rangeEnd) {
        return ResponseEntity.ok(
                driveService.getGPSDrives(vehicleId, rangeStart.toLocalDateTime(), rangeEnd.toLocalDateTime())
                        .stream()
                        .map(gpsDtoToGPSResponseDataMapper::map)
                        .toList()
        );
    }
}
