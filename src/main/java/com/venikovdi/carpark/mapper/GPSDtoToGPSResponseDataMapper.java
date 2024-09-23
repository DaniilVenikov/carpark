package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.api.data.GPSResponseData;
import com.venikovdi.carpark.data.dto.GPSDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface GPSDtoToGPSResponseDataMapper {
    @Mapping(target = "timestamp", source = "timestamp")
    @Mapping(target = "latitude", expression = "java(gpsDto.geom().getY())")
    @Mapping(target = "longitude", expression = "java(gpsDto.geom().getX())")
    GPSResponseData map(GPSDto gpsDto);
}
