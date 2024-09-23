package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.api.data.EnterprisePutRequestData;
import com.venikovdi.carpark.data.dto.EnterpriseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnterprisePutRequestDataToEnterpriseDtoMapper {

    EnterpriseDto map(EnterprisePutRequestData enterprisePutRequestData);
}
