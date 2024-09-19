package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.api.data.GPSFilterData;
import com.venikovdi.carpark.data.dto.GPSFilter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
@Mapper(componentModel = SPRING)
public interface GPSFilterDataToGPSFilterMapper {

//    @Mapping(target = "rangeStart", source = "rangeStart", qualifiedByName = "mapRangeStart")
//    @Mapping(target = "rangeEnd", source = "rangeEnd", qualifiedByName = "mapRangeEnd")
    GPSFilter map(GPSFilterData gpsFilterData);

//    @Named("mapRangeStart")
//    default LocalDateTime mapRangeStart(OffsetDateTime rangeStart) {
//        return rangeStart.toLocalDateTime();
//    }
//    @Named("mapRangeEnd")
//    default LocalDateTime mapRangeEnd(OffsetDateTime rangeEnd) {
//        return rangeEnd.toLocalDateTime();
//    }
}
