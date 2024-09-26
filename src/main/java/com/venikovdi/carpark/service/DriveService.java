package com.venikovdi.carpark.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.venikovdi.carpark.data.dto.DriveDto;
import com.venikovdi.carpark.data.dto.GPSDto;
import com.venikovdi.carpark.mapper.DriveToDriveDtoMapper;
import com.venikovdi.carpark.mapper.GPSToGPSDtoMapper;
import com.venikovdi.carpark.repository.DriveRepository;
import com.venikovdi.carpark.repository.GPSRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DriveService {
    private final DriveRepository driveRepository;
    private final DriveToDriveDtoMapper driveToDriveDtoMapper;
    private final GPSRepository gpsRepository;
    private final GPSToGPSDtoMapper gpsToGPSDtoMapper;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static final String KEY = "pk.003631cfe194a2b50f21974ab75df8c9";
    private static final String URL = "https://us1.locationiq.com/v1/reverse";

    public List<GPSDto> getGPSDrives(int vehicleId, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        List<DriveDto> drives = getDrives(vehicleId, rangeStart, rangeEnd);
        return getGPSFromDrives(drives);
    }

    public List<DriveDto> getDrives(int vehicleId, LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        return driveRepository.findAllByVehicleIdAndStartGreaterThanEqualAndEndLessThanEqual(
                        vehicleId,
                        rangeStart,
                        rangeEnd)
                .stream()
                .map(driveToDriveDtoMapper::map)
                .toList();
    }

    public List<GPSDto> getGPSFromDrives(List<DriveDto> drives) {
        return drives.stream()
                .flatMap(driveDto -> gpsRepository.getGPS(driveDto.vehicleId(), driveDto.startingTime(), driveDto.endTime())
                        .stream()
                        .map(gpsToGPSDtoMapper::map))
                .toList();
    }

    public String getAddress(double latitude, double longitude) {
        try {
            return getFullAddressData(latitude, longitude);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to retrieve address", e);
        }
    }

    private String getFullAddressData(double latitude, double longitude) throws JsonProcessingException {
        String url = UriComponentsBuilder.fromHttpUrl(URL)
                .queryParam("key", KEY)
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("format", "json")
                .toUriString();

        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode rootNode = objectMapper.readTree(response);
            return rootNode.path("display_name").asText();
        } catch (Exception e) {
            log.error("Произошла ошибка при получении физического адреса: " + e.getMessage());
            throw e;
        }
    }
}
