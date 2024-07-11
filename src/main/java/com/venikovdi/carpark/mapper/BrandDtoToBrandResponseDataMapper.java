package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.api.data.BrandResponseData;
import com.venikovdi.carpark.data.dto.BrandDto;
import com.venikovdi.carpark.entity.en.VehicleType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BrandDtoToBrandResponseDataMapper {

    @Mapping(source = "title", target = "title")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "volumeTank", target = "volumeTank")
    @Mapping(source = "liftingCapacity", target = "liftingCapacity")
    @Mapping(source = "numberSeats", target = "numberSeats")
    BrandResponseData map(BrandDto brandDto);

    default String mapType(VehicleType type) {
        return type.name();
    }
}
