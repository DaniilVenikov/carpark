package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.api.data.EnterprisePutRequestData;
import com.venikovdi.carpark.data.dto.EnterpriseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EnterprisePutRequestDataToEnterpriseDtoMapper {

    EnterpriseDto map(EnterprisePutRequestData enterprisePutRequestData);
}
