package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.api.data.VehiclePutRequestData;
import com.venikovdi.carpark.data.dto.VehicleDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface VehiclePutRequestDataToVehicleDtoMapper {

    VehicleDto map(VehiclePutRequestData vehiclePutRequestData);
}
