package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.data.dto.VehicleDto;
import com.venikovdi.carpark.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface VehicleDtoToVehicleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", source = "price")
    @Mapping(target = "mileage", source = "mileage")
    @Mapping(target = "releaseYear", source = "releaseYear")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "drivers", ignore = true)
    Vehicle map(VehicleDto vehicleDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", source = "price", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "mileage", source = "mileage", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "releaseYear", source = "releaseYear", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "color", source = "color", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "number", source = "number", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "enterprise", ignore = true)
    @Mapping(target = "drivers", ignore = true)
    Vehicle update(@MappingTarget Vehicle vehicle, VehicleDto vehicleDto);
}
