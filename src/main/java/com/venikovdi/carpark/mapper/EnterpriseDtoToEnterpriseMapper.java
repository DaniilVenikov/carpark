package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.data.dto.EnterpriseDto;
import com.venikovdi.carpark.entity.Enterprise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EnterpriseDtoToEnterpriseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "vehicles", ignore = true)
    Enterprise map(EnterpriseDto enterpriseDto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "title", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "city", source = "city", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "vehicles", ignore = true)
    @Mapping(target = "drivers", ignore = true)
    @Mapping(target = "managers", ignore = true)
    Enterprise update(@MappingTarget Enterprise enterprise, EnterpriseDto enterpriseDto);
}
