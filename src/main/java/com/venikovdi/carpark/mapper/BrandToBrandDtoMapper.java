package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.data.dto.BrandDto;
import com.venikovdi.carpark.entity.Brand;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BrandToBrandDtoMapper {

    BrandDto map(Brand brand);
}
