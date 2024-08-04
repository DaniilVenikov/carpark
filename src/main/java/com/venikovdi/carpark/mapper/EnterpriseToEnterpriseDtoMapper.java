package com.venikovdi.carpark.mapper;

import com.venikovdi.carpark.data.dto.EnterpriseDto;
import com.venikovdi.carpark.entity.Enterprise;
import com.venikovdi.carpark.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface EnterpriseToEnterpriseDtoMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "vehicles", source = "vehicles")
    EnterpriseDto map(Enterprise enterprise);

    default List<Integer> mapVehicles(Collection<Vehicle> vehicles) {
        if (vehicles == null) {
            return List.of();
        }
        return vehicles
                .stream()
                .map(Vehicle::getId)
                .toList();
    }
}
