package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.data.dto.GPSDto;
import com.venikovdi.carpark.entity.GPS;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
@Mapper(componentModel = SPRING)
public interface GPSToGPSDtoMapper {

    @Mapping(target = "vehicleId", expression = "java(gps.getVehicle().getId())")
    @Mapping(target = "timestamp", source = "timestamp")
    @Mapping(target = "geom", source = "geom")
    GPSDto map(GPS gps);
}
