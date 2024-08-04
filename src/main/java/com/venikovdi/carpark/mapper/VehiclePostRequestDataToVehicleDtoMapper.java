package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.api.data.VehiclePostRequestData;
import com.venikovdi.carpark.data.dto.VehicleDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface VehiclePostRequestDataToVehicleDtoMapper {

    VehicleDto map(VehiclePostRequestData vehiclePostRequestData);
}
