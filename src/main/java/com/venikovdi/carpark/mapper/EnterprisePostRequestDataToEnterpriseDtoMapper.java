package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.api.data.EnterprisePostRequestData;
import com.venikovdi.carpark.data.dto.EnterpriseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EnterprisePostRequestDataToEnterpriseDtoMapper {
    @Mapping(target = "vehicles", ignore = true)
    EnterpriseDto map(EnterprisePostRequestData enterprisePostRequestData);
}
