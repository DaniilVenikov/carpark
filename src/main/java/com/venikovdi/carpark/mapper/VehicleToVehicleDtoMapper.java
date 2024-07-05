package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.data.dto.VehicleDto;
import com.venikovdi.carpark.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface VehicleToVehicleDtoMapper {

    @Mapping(target = "brandId", source = "brand.id")
    VehicleDto map(Vehicle vehicle);
}
