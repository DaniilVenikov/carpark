package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.api.data.VehicleResponseData;
import com.venikovdi.carpark.data.dto.VehicleDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface VehicleDtoToVehicleResponseDataMapper {

    VehicleResponseData map(VehicleDto vehicleDto);
}
