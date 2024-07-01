package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.data.dto.VehicleDto;
import com.venikovdi.carpark.entity.Vehicle;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface VehicleToVehicleDtoMapper {

    VehicleDto map(Vehicle vehicle);
}
