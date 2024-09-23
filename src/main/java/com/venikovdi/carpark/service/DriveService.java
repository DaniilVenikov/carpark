package com.venikovdi.carpark.service;

import com.venikovdi.carpark.data.dto.DriveDto;
import com.venikovdi.carpark.data.dto.GPSDto;
import com.venikovdi.carpark.mapper.DriveToDriveDtoMapper;
import com.venikovdi.carpark.mapper.GPSToGPSDtoMapper;
import com.venikovdi.carpark.repository.DriveRepository;
import com.venikovdi.carpark.repository.GPSRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriveService {
    private final DriveRepository driveRepository;
    private final DriveToDriveDtoMapper driveToDriveDtoMapper;
    private final GPSRepository gpsRepository;
    private final GPSToGPSDtoMapper gpsToGPSDtoMapper;

    public List<GPSDto> getGPSDrives(int vehicleId, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        List<DriveDto> drives = getDrives(vehicleId, rangeStart, rangeEnd);

        List<GPSDto> gpsDrives = new ArrayList<>();
        drives.forEach(
                driveDto ->
                        gpsDrives.addAll(gpsRepository.getGPS(driveDto.vehicleId(), driveDto.start(), driveDto.end())
                                .stream()
                                .map(gpsToGPSDtoMapper::map)
                                .toList())
        );

        return gpsDrives;
    }

    private List<DriveDto> getDrives(int vehicleId, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        return driveRepository.findAllByVehicleIdAndStartGreaterThanEqualAndEndLessThanEqual(
                        vehicleId,
                        rangeStart,
                        rangeEnd)
                .stream()
                .map(driveToDriveDtoMapper::map)
                .toList();
    }
}
