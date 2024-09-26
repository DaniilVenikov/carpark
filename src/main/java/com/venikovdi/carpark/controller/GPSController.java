package com.venikovdi.carpark.controller;

import com.venikovdi.carpark.api.GPSApi;
import com.venikovdi.carpark.api.data.GPSFilterData;
import com.venikovdi.carpark.data.dto.GPSDto;
import com.venikovdi.carpark.mapper.GPSDtoToGPSResponseDataMapper;
import com.venikovdi.carpark.service.GPSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.venikovdi.carpark.converter.GeoJSONConverter.convertToGeoJson;

@RestController
@RequiredArgsConstructor
public class GPSController implements GPSApi {
    private final GPSService gpsService;
    private final GPSDtoToGPSResponseDataMapper gpsDtoToGPSResponseDataMapper;

    @Override
    public ResponseEntity<?> get(Integer vehicleId, GPSFilterData gpsFilterData) {
        List<GPSDto> gpsDtoList = gpsService
                .getGPSData(
                        vehicleId,
                        gpsFilterData.rangeStart().toLocalDateTime(),
                        gpsFilterData.rangeEnd().toLocalDateTime()
                );

        if(gpsFilterData.geoJSON()) {
            return ResponseEntity.ok(convertToGeoJson(gpsDtoList));
        }

        return ResponseEntity.ok(gpsDtoList
                .stream()
                .map(gpsDtoToGPSResponseDataMapper::map)
                .toList());
    }
}
