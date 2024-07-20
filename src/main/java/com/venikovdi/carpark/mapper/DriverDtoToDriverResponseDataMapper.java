package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.api.data.DriverResponseData;
import com.venikovdi.carpark.data.dto.DriverDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DriverDtoToDriverResponseDataMapper {

    DriverResponseData map(DriverDto driverDto);
}
