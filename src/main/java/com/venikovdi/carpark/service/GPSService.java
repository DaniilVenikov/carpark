package com.venikovdi.carpark.service;

import com.venikovdi.carpark.data.dto.GPSDto;
import com.venikovdi.carpark.mapper.GPSToGPSDtoMapper;
import com.venikovdi.carpark.repository.GPSRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GPSService {
    private final GPSRepository gpsRepository;
    private final GPSToGPSDtoMapper gpsToGPSDtoMapper;

    public List<GPSDto> getGPSData(Integer vehicleId, LocalDateTime rangeStart, LocalDateTime rangeEnd){
        return gpsRepository
                .getGPS(vehicleId, rangeStart, rangeEnd)
                .stream()
                .map(gpsToGPSDtoMapper::map)
                .toList();
    }
}
