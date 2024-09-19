package com.venikovdi.carpark.service;

import com.venikovdi.carpark.data.dto.GPSDto;
import com.venikovdi.carpark.data.dto.GPSFilter;
import com.venikovdi.carpark.mapper.GPSToGPSDtoMapper;
import com.venikovdi.carpark.repository.GPSRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GPSService {
    private final GPSRepository gpsRepository;
    private final GPSToGPSDtoMapper gpsToGPSDtoMapper;

    public List<GPSDto> getVehicleCoordinates(Integer vehicleId, GPSFilter gpsFilter){
        return gpsRepository
                .getGPS(vehicleId, gpsFilter.rangeStart().toLocalDateTime(), gpsFilter.rangeEnd().toLocalDateTime())
                .stream()
                .map(gpsToGPSDtoMapper::map)
                .toList();
    }
}
