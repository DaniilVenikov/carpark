package com.venikovdi.carpark.converter;

import com.venikovdi.carpark.data.dto.GPSDto;
import lombok.Data;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeoJSONConverter {
    public static GeoJson convertToGeoJson(Collection<GPSDto> gpsData) {
        List<Feature> features = gpsData.stream()
                .map(GeoJSONConverter::convertToGeoJsonFeature)
                .toList();

        return new GeoJson(features);
    }
    private static Feature convertToGeoJsonFeature(GPSDto gpsDto) {
        Point geometry = new Point(gpsDto.geom().getY(), gpsDto.geom().getX());
        Map<String, Object> properties = new HashMap<>();
        properties.put("timestamp", gpsDto.timestamp());

        return new Feature("GPS", geometry, properties);
    }

    @Data
    private static class GeoJson {
        private String type = "GPS Data Collection";
        private List<Feature> features;

        public GeoJson(List<Feature> features) {
            this.features = features;
        }
    }

    @Data
    private static class Feature {
        private String type;
        private Point geometry;
        private Map<String, Object> properties;

        public Feature(String type, Point geometry, Map<String, Object> properties) {
            this.type = type;
            this.geometry = geometry;
            this.properties = properties;
        }
    }

    @Data
    private static class Point {
        private String type = "Point";
        private double[] coordinates;

        public Point(double longitude, double latitude) {
            this.coordinates = new double[]{longitude, latitude};
        }
    }
}
