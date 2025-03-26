package com.venikovdi.carpark.infra.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.LatLng;
import com.venikovdi.carpark.domain.entity.GPS;
import com.venikovdi.carpark.domain.entity.Vehicle;
import com.venikovdi.carpark.infra.repository.GPSRepository;
import com.venikovdi.carpark.infra.repository.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ShellComponent
public class TrackGenerator {
    private final GPSRepository gpsRepository;
    private final VehicleRepository vehicleRepository;
    private final WebClient webClient;
    private final GeometryFactory geometryFactory;
    private final List<double[]> routePoints = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger currentPointIndex = new AtomicInteger(0);

    private Integer vehicleId;
    private final AtomicBoolean trackingActive = new AtomicBoolean(false);
    private static final String TOKEN = "5b3ce3597851110001cf624875919968565c48c1a0f9c186e971c15b";
    private static final String URL = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=" + TOKEN;

    public TrackGenerator(GPSRepository gpsRepository, VehicleRepository vehicleRepository, WebClient.Builder webClientBuilder) {
        this.gpsRepository = gpsRepository;
        this.vehicleRepository = vehicleRepository;
        this.webClient = webClientBuilder.baseUrl(URL).build();
        this.geometryFactory = new GeometryFactory();
    }

    @ShellMethod(key = "generate-track")
    public void generateTrack(
            @Option(required = true) Integer vehicleId,
            @ShellOption(defaultValue = "55.7522") double startLat,
            @ShellOption(defaultValue = "37.6156") double startLng,
            @ShellOption(defaultValue = "51.672") double endLat,
            @ShellOption(defaultValue = "39.1843") double endLng
    ) {
        this.vehicleId = vehicleId;
        generateRoute(startLat, startLng, endLat, endLng)
                .subscribe();
    }

    private Mono<Void> generateRoute(double startLat, double startLng, double endLat, double endLng) {
        return sendRouteRequest(startLat, startLng, endLat, endLng)
                .doOnSuccess(this::processRouteResponse)
                .onErrorResume(e -> {
                    log.error("Ошибка при генерации маршрута", e);
                    return Mono.empty();
                })
                .then();
    }

    private Mono<JsonNode> sendRouteRequest(double startLat, double startLng, double endLat, double endLng) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode requestJson = objectMapper.createObjectNode();
        ArrayNode coordinates = objectMapper.createArrayNode();

        coordinates.add(objectMapper.createArrayNode().add(startLng).add(startLat));
        coordinates.add(objectMapper.createArrayNode().add(endLng).add(endLat));
        requestJson.set("coordinates", coordinates);

        return webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestJson.toString())
                .retrieve()
                .bodyToMono(JsonNode.class);
    }

    private void processRouteResponse(JsonNode responseBody) {
        JsonNode routes = responseBody.path("routes");
        if (routes.isArray() && routes.size() > 0) {
            String encodedPolyline = routes.get(0).path("geometry").asText();
            List<LatLng> generatedTrack = PolylineEncoding.decode(encodedPolyline);

            synchronized (routePoints) {
                routePoints.clear();
                generatedTrack.forEach(point -> routePoints.add(new double[]{point.lat, point.lng}));
            }

            log.info("Маршрут сгенерирован. Точек в маршруте: " + routePoints.size());
            currentPointIndex.set(0);
            trackingActive.set(true);
        } else {
            log.error("Не удалось извлечь маршрут из ответа.");
        }
    }

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void updateTrackInRealTime() {
        if (!trackingActive.get()) {
            return;
        }

        if (currentPointIndex.get() < routePoints.size()) {
            double[] currentPoint;
            synchronized (routePoints) {
                currentPoint = routePoints.get(currentPointIndex.getAndIncrement());
            }

            saveTrackPoint(currentPoint)
                    .doOnError(e -> log.error("Ошибка при сохранении точки трека", e))
                    .subscribe();
        } else {
            log.info("Маршрут завершён.");
            trackingActive.set(false);
        }
    }

    private Mono<Void> saveTrackPoint(double[] currentPoint) {
        return Mono.fromCallable(() -> {
            Vehicle vehicle = vehicleRepository.findById(vehicleId)
                    .orElseThrow(() -> new IllegalArgumentException("Машина с ID " + vehicleId + " не найдена."));

            Coordinate coordinate = new Coordinate(currentPoint[1], currentPoint[0]);
            Point point = geometryFactory.createPoint(coordinate);
            point.setSRID(4326);

            GPS gpsData = new GPS();
            gpsData.setVehicle(vehicle);
            gpsData.setTimestamp(LocalDateTime.now());
            gpsData.setGeom(point);
            gpsRepository.save(gpsData);
            return null;
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }
}
