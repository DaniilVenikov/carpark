package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.data.dto.DriverDto;
import com.venikovdi.carpark.entity.Driver;
import com.venikovdi.carpark.entity.Enterprise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DriverToDriverDtoMapper {

    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "salary", source = "salary")
    @Mapping(target = "enterpriseId", source = "enterprise")

    DriverDto map(Driver driver);

    default Integer mapEnterprise(Enterprise enterprise) {
        return enterprise == null ? null : enterprise.getId();
    }
}
