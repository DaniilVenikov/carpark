package com.venikovdi.carpark.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;
import com.venikovdi.carpark.entity.GPS;
import com.venikovdi.carpark.entity.Vehicle;
import com.venikovdi.carpark.repository.GPSRepository;
import com.venikovdi.carpark.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ShellComponent
public class TrackGenerator {

    private final GPSRepository gpsRepository;
    private final VehicleRepository vehicleRepository;
    private final RestTemplate restTemplate;
    private final GeometryFactory geometryFactory;
    private final List<double[]> routePoints;
    private int currentPointIndex = 0;

    private Integer vehicleId;
    private boolean trackingActive = false;

    private static final String TOKEN = "5b3ce3597851110001cf624875919968565c48c1a0f9c186e971c15b";
    private static final String URL = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=";

    public TrackGenerator(GPSRepository gpsRepository, VehicleRepository vehicleRepository) {
        this.gpsRepository = gpsRepository;
        this.vehicleRepository = vehicleRepository;
        this.restTemplate = new RestTemplate();
        this.routePoints = new ArrayList<>();
        this.geometryFactory = new GeometryFactory();
    }

    @ShellMethod(key = "generate-track")
    public void generateTrack(
            @Option(required = true) Integer vehicleId,
            @ShellOption(defaultValue = "55.7539") double startLat,
            @ShellOption(defaultValue = "37.6208") double startLng,
            @ShellOption(defaultValue = "55.8296") double endLat,
            @ShellOption(defaultValue = "37.6336") double endLng
    ) {
        this.vehicleId = vehicleId;  // Сохраняем ID машины
        try {
            generateRoute(startLat, startLng, endLat, endLng);
        } catch (Exception e) {
            log.error("Ошибка при генерации маршрута: ", e);
        }
    }

    private void generateRoute(double startLat, double startLng, double endLat, double endLng) {
        ResponseEntity<JsonNode> response = sendRouteRequest(startLat, startLng, endLat, endLng);

        if (response.getStatusCode().is2xxSuccessful() && response.hasBody()) {
            processRouteResponse(response.getBody());
        } else {
            log.error("Ошибка при получении маршрута: " + response.getStatusCode());
        }
    }

    private ResponseEntity<JsonNode> sendRouteRequest(double startLat, double startLng, double endLat, double endLng) {
        String url = URL + TOKEN;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJson = objectMapper.createObjectNode();
        ArrayNode coordinates = objectMapper.createArrayNode();

        coordinates.add(objectMapper.createArrayNode().add(startLng).add(startLat));
        coordinates.add(objectMapper.createArrayNode().add(endLng).add(endLat));
        requestJson.set("coordinates", coordinates);

        HttpEntity<String> entity = new HttpEntity<>(requestJson.toString(), headers);

        log.debug("Отправка запроса на маршрутизацию: " + requestJson);
        return restTemplate.exchange(url, HttpMethod.POST, entity, JsonNode.class);
    }

    private void processRouteResponse(JsonNode responseBody) {
        JsonNode routes = responseBody.path("routes");
        if (routes.isArray() && routes.size() > 0) {
            String encodedPolyline = routes.get(0).path("geometry").asText();
            List<LatLng> generateTrack = PolylineEncoding.decode(encodedPolyline);

            routePoints.clear(); // Очищаем предыдущие точки
            generateTrack.forEach(point -> routePoints.add(new double[]{point.lat, point.lng}));

            log.info("Маршрут сгенерирован. Точек в маршруте: " + routePoints.size());

            currentPointIndex = 0;
            trackingActive = true;
        } else {
            log.error("Не удалось извлечь маршрут из ответа.");
        }
    }

    @Scheduled(fixedRate = 10000) // Обновляем каждые 10 секунд
    @Transactional
    public void updateTrackInRealTime() {
        if (!trackingActive) {
            return;
        }

        if (currentPointIndex < routePoints.size()) {
            try {
                double[] currentPoint = routePoints.get(currentPointIndex);
                saveTrackPoint(currentPoint);
                currentPointIndex++;
                log.info("Добавлена точка в трек: " + currentPoint[0] + ", " + currentPoint[1]);
            } catch (Exception e) {
                log.error("Ошибка при сохранении точки трека: ", e);
            }
        } else {
            log.info("Маршрут завершён.");
            trackingActive = false;  // Останавливаем трек после завершения
        }
    }

    private void saveTrackPoint(double[] currentPoint) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new IllegalArgumentException("Машина с ID " + vehicleId + " не найдена."));

        // Создаем геометрическую точку
        Coordinate coordinate = new Coordinate(currentPoint[1], currentPoint[0]); // Координаты (долгота, широта)
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(4326); // EPSG:4326 (WGS 84)

        // Сохраняем точку в базе
        GPS gpsData = new GPS();
        gpsData.setVehicle(vehicle);
        gpsData.setTimestamp(LocalDateTime.now());
        gpsData.setGeom(point);
        gpsRepository.save(gpsData);
    }
}
