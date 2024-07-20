package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.api.data.EnterpriseResponseData;
import com.venikovdi.carpark.data.dto.EnterpriseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EnterpriseDtoToEnterpriseResponseDataMapper {

    EnterpriseResponseData map(EnterpriseDto enterpriseDto);
}
