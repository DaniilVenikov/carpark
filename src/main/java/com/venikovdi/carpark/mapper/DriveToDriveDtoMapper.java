package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.data.dto.DriveDto;
import com.venikovdi.carpark.entity.Drive;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DriveToDriveDtoMapper {

    @Mapping(target = "vehicleId", expression = "java(drive.getVehicle().getId())")
    @Mapping(target = "startingTime", source = "start")
    @Mapping(target = "endTime", source = "end")
    DriveDto map(Drive drive);
}
