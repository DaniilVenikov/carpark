package com.venikovdi.carpark.controller;

import com.venikovdi.carpark.api.DriveApi;
import com.venikovdi.carpark.api.data.DriveResponseData;
import com.venikovdi.carpark.api.data.GPSResponseData;
import com.venikovdi.carpark.data.dto.DriveDto;
import com.venikovdi.carpark.data.dto.GPSDto;
import com.venikovdi.carpark.mapper.GPSDtoToGPSResponseDataMapper;
import com.venikovdi.carpark.service.DriveService;
import com.venikovdi.carpark.service.GPSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class DriveController implements DriveApi {
    private final DriveService driveService;
    private final GPSService gpsService;
    private final GPSDtoToGPSResponseDataMapper gpsDtoToGPSResponseDataMapper;
    @Override
    public ResponseEntity<Collection<GPSResponseData>> getDriveGps(Integer vehicleId,
                                                                   OffsetDateTime rangeStart,
                                                                   OffsetDateTime rangeEnd) {
        return ResponseEntity.ok(
                driveService.getGPSDrives(vehicleId, rangeStart.toLocalDateTime(), rangeEnd.toLocalDateTime())
                        .stream()
                        .map(gpsDtoToGPSResponseDataMapper::map)
                        .toList()
        );
    }

    @Override
    public ResponseEntity<Collection<DriveResponseData>> getDrive(Integer vehicleId, OffsetDateTime rangeStart, OffsetDateTime rangeEnd) {
        List<DriveDto> drives = driveService.getDrives(vehicleId, rangeStart.toLocalDateTime(), rangeEnd.toLocalDateTime());
        List<DriveResponseData> responseData = new ArrayList<>();

        for (DriveDto driveDto : drives) {
            List<GPSDto> gps = gpsService.getGPSData(
                    driveDto.vehicleId(),
                    driveDto.startingTime(),
                    driveDto.endTime()
            );

            if (gps.isEmpty()) continue;

            GPSDto startGPS = gps.get(0);
            GPSDto endGPS = gps.get(gps.size() - 1);

            String startingAddress = driveService.getAddress(startGPS.geom().getY(), startGPS.geom().getX());
            String endAddress = driveService.getAddress(endGPS.geom().getY(), endGPS.geom().getX());

            responseData.add(
                    new DriveResponseData(
                            driveDto.startingTime(),
                            driveDto.endTime(),
                            startGPS.geom().getY(),
                            startGPS.geom().getX(),
                            endGPS.geom().getY(),
                            endGPS.geom().getX(),
                            startingAddress,
                            endAddress
                    )
            );
        }
        return ResponseEntity.ok(responseData);
    }
}
